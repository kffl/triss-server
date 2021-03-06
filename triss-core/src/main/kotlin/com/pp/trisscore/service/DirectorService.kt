package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class DirectorService(val employeeService: EmployeeService,
                      val applicationService: ApplicationService,
                      val applicationFullService: ApplicationFullService,
                      val validationService: ValidationService,
                      val comparisonService: ComparisonService,
                      val financialSourceService: FinancialSourceService) {


    val role = Role.DIRECTOR
    fun getApplications(pageInfo: PageInfo<ApplicationRow>, tokenBody: TokenData): Flux<ApplicationRow> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
                .switchIfEmpty(Mono.error(UnauthorizedException(tokenBody.employeeId.toString(), tokenBody.firstname, tokenBody.surname)))
                .flatMapMany { x -> applicationService.getAllByFilter(pageInfo.copy(pageInfo.filter.copy(instituteId = x!!.instituteID!!))) }
    }

    fun getCountByFilter(tokenBody: TokenData, pageInfo: PageInfo<ApplicationRow>): Mono<Long> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
                .switchIfEmpty(Mono.error(UnauthorizedException(tokenBody.employeeId.toString(), tokenBody.firstname, tokenBody.surname)))
                .flatMap { x -> applicationService.getCountByFilter(pageInfo.copy(pageInfo.filter.copy(instituteId = x!!.instituteID!!))) }
    }

    @Transactional
    fun approveApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
        validationService.validateApproveApplicationInfo(body, role)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
                .switchIfEmpty(Mono.error(UnauthorizedException(tokenBody.employeeId.toString(), tokenBody.firstname, tokenBody.surname)))
                .flatMap { x -> applicationFullService.getFullDirectorApplication(body.application.id!!, x!!) }
                .flatMap { x -> validateApproveAndSaveApplication(x, body) }
    }

    fun validateApproveAndSaveApplication(dbApplication: ApplicationInfo, reqApplication: ApplicationInfo): Mono<Application> {
        comparisonService.compareApplicationsInfo(dbApplication, reqApplication, Role.DIRECTOR)
        return financialSourceService.save(reqApplication.financialSource!!)
                .flatMap { x -> applicationService.saveApplication(reqApplication.application.copy(status = StatusEnum.WaitingForWilda.value,financialSourceId = x.id)) }

    }


    fun getFullApplication(tokenBody: TokenData, id: Long): Mono<ApplicationInfo> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
                .switchIfEmpty(Mono.error(UnauthorizedException(tokenBody.employeeId.toString(), tokenBody.firstname, tokenBody.surname)))
                .flatMap { x -> applicationFullService.getFullDirectorApplication(id, x!!) }
    }


    @Transactional
    fun rejectApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
        validationService.validateRejectApplicationInfo(body, role)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
                .switchIfEmpty(Mono.error(UnauthorizedException(tokenBody.employeeId.toString(), tokenBody.firstname, tokenBody.surname)))
                .flatMap { x -> applicationFullService.getFullDirectorApplication(body.application.id!!, x!!) }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Application")))
                .flatMap { x -> validateRejectAndSaveApplication(x, body) }

    }

    private fun validateRejectAndSaveApplication(dbApplication: ApplicationInfo, reqApplication: ApplicationInfo): Mono<Application> {
        comparisonService.compareApplicationsInfo(dbApplication, reqApplication, role)
        return applicationService.saveApplication(reqApplication.application.copy(status = StatusEnum.RejectedByDirector.value))
    }
}
