package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class UserService(val employeeService: EmployeeService,
                  val applicationService: ApplicationService,
                  val applicationFullService: ApplicationFullService) {
    val role: Role = Role.USER

    fun getMyApplications(tokenData: TokenData, body: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        if (tokenData.employeeId != body.filter.employeeId)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        return employeeService.findEmployee(tokenData)
                .flatMapMany { x -> applicationService.getAllByFilter(body) }
    }

    fun getCountByFilter(tokenData: TokenData, body: PageInfo<ApplicationRow>): Mono<Long> {
        if (tokenData.employeeId != body.filter.employeeId)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        return employeeService.findEmployee(tokenData)
                .flatMap { x -> applicationService.getCountByFilter(body) }
    }

    fun getMyFullApplication(tokenData: TokenData, body:Long): Mono<ApplicationInfo> {
        return applicationFullService.getFullUserApplication(body,tokenData)
    }

    fun createApplication(tokenData: TokenData, body: ApplicationInfo): Mono<Transport> {
        return employeeService.findEmployeeAndCheckRole(tokenData, role)
                .flatMap { x -> applicationService.createApplication(body, x) }
    }


}
