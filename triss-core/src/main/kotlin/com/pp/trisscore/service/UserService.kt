package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class UserService(val tokenService: TokenService,
                  val employeeService: EmployeeService,
                  val applicationService: ApplicationService,
                  val applicationFullService: ApplicationFullService) {
    val role: Role = Role.USER

    fun getMyApplications(token: JwtAuthenticationToken, body: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        if (tokenData.employeeId != body.filter.employeeId)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        return employeeService.findEmployeeAndCheckRole(tokenData, role)
                .flatMapMany { x -> applicationService.getAllByFilter(body) }
    }

    fun getCountByFilter(token: JwtAuthenticationToken, body: PageInfo<ApplicationRow>): Mono<Long> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        if (tokenData.employeeId != body.filter.employeeId)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        return employeeService.findEmployeeAndCheckRole(tokenData, role)
                .flatMap { x -> applicationService.getCountByFilter(body) }
    }

    fun getMyFullApplication(token: JwtAuthenticationToken,body:Long): Mono<ApplicationInfo> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return applicationFullService.getFullUserApplication(body,tokenData)
    }

    fun createApplication(token: JwtAuthenticationToken, body: ApplicationInfo): Mono<Transport> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return employeeService.findEmployeeAndCheckRole(tokenData, role)
                .flatMap { x -> applicationService.createApplication(body, x) }
    }


}
