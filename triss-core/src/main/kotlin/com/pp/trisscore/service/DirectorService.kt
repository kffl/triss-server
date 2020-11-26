package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.Status
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DirectorService(val employeeService: EmployeeService,
                      val applicationService: ApplicationService,
                      val applicationFullService: ApplicationFullService,
                      val validationService: ValidationService) {


    val role = Role.DIRECTOR
    fun getApplications(pageInfo: PageInfo<ApplicationRow>, tokenBody: TokenData): Flux<ApplicationRow> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMapMany { x -> applicationService.getAllByFilter(pageInfo.copy(pageInfo.filter.copy(instituteId = x!!.instituteID!!))) }
    }

    fun getCountByFilter(tokenBody: TokenData, pageInfo: PageInfo<ApplicationRow>): Mono<Long> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationService.getCountByFilter(pageInfo.copy(pageInfo.filter.copy(instituteId = x!!.instituteID!!))) }
    }

    fun approveApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
        validationService.validateFinancialSource(body.financialSource)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationFullService.getFullDirectorApplication(body.application.id!!,x!!) }
                .flatMap { x -> applicationService.approveApplication(body, x!!) }
    }



    fun getFullApplication(tokenBody: TokenData, id: Long): Mono<ApplicationInfo> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationFullService.getFullDirectorApplication(id, x!!) }
    }
}
