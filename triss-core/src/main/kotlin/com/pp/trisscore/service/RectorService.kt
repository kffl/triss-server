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
class RectorService(
        private val employeeService: EmployeeService,
        private val applicationService: ApplicationService,
        private val applicationFullService: ApplicationFullService,
        val comparisonService: ComparisonService,
        val validationService: ValidationService) {

    private val role = Role.RECTOR

    fun getApplications(pageInfo: PageInfo<ApplicationRow>, tokenBody: TokenData): Flux<ApplicationRow> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("You do not have permission to perform this action")))
                .flatMapMany { applicationService.getAllByFilter(pageInfo.copy(pageInfo.filter.copy(status = StatusEnum.WaitingForRector.value))) }
    }

    fun getCountByFilter(tokenBody: TokenData, pageInfo: PageInfo<ApplicationRow>): Mono<Long> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("You do not have permission to perform this action")))
                .flatMap { applicationService.getCountByFilter(pageInfo.copy(pageInfo.filter.copy(status = StatusEnum.WaitingForRector.value))) }
    }

    fun approveApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
         validationService.validateApproveApplicationInfo(body,role)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { applicationFullService.getFullApplication(body.application.id!!) }
                .flatMap { x -> validateApproveAndSaveApplication(x, body) }
    }

    private fun validateApproveAndSaveApplication(dbApplicationInfo: ApplicationInfo?, reqApplicationInfo: ApplicationInfo): Mono<out Application>? {
        comparisonService.compareApplicationsInfo(dbApplicationInfo!!, reqApplicationInfo, role)
        return applicationService.saveApplication(reqApplicationInfo.application.copy(status = StatusEnum.Accepted.value))
    }

    fun getFullApplication(tokenBody: TokenData, id: Long): Mono<ApplicationInfo> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { applicationFullService.getFullApplication(id) }
    }

    @Transactional
    fun rejectApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
        validationService.validateRejectApplicationInfo(body, role)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
                .switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationFullService.getFullDirectorApplication(body.application.id!!, x!!) }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("")))
                .flatMap { x -> validateRejectAndSaveApplication(x, body) }
    }

    private fun validateRejectAndSaveApplication(dbApplication: ApplicationInfo, reqApplication: ApplicationInfo): Mono<Application> {
        comparisonService.compareApplicationsInfo(dbApplication, reqApplication, role)
        return applicationService.saveApplication(reqApplication.application.copy(status = StatusEnum.RejectedByRector.value))
    }

}
