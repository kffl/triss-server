package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.Status
import com.pp.trisscore.model.rows.ApplicationFull
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DirectorService(val tokenService: TokenService,
                      val employeeService: EmployeeService,
                      val applicationService: ApplicationService,
                      val applicationFullService: ApplicationFullService) {


    val role = Role.DIRECTOR
    fun getApplications(pageInfo: PageInfo<ApplicationRow>, token: JwtAuthenticationToken): Flux<ApplicationRow> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMapMany { x -> applicationService.getAllByFilter(pageInfo.copy(pageInfo.filter.copy(instituteId = x!!.instituteID!!))) }
    }

    fun getCountByFilter(token: JwtAuthenticationToken, pageInfo: PageInfo<ApplicationRow>): Mono<Long> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationService.getCountByFilter(pageInfo.copy(pageInfo.filter.copy(instituteId = x!!.instituteID!!))) }
    }

    fun approveApplication(token: JwtAuthenticationToken, body: ApplicationInfo): Mono<Application> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        validateApprove(body)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationService.approveApplication(body, x!!) }
    }

    fun validateApprove(data :ApplicationInfo) {
        if(data.financialSource == null)
            throw InvalidRequestBodyException("")
        val financialSource = data.financialSource
        if (financialSource.MPK != null)
            throw InvalidRequestBodyException("")
        if (financialSource.allocationAccount != null)
            throw InvalidRequestBodyException("")
        if (financialSource.financialSource != null)
            throw InvalidRequestBodyException("")
        if (financialSource.project != null)
            throw InvalidRequestBodyException("")
        if(data.application.status != Status.WaitingForWilda)
            throw InvalidRequestBodyException("")
    }


    fun getFullApplication(token: JwtAuthenticationToken, id: Long): Mono<ApplicationInfo> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationFullService.getFullDirectorApplication(id, x!!) }
    }


}
