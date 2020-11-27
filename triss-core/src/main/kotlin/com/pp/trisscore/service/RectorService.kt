package com.pp.trisscore.service

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
class RectorService(
        private val employeeService: EmployeeService,
        private val applicationService: ApplicationService,
        private val applicationFullService: ApplicationFullService,
        val comparisonService: ComparisonService) {

    private val role = Role.RECTOR

    //get applications which are waiting for Rector acceptation
    fun getApplications(pageInfo: PageInfo<ApplicationRow>, tokenBody: TokenData): Flux<ApplicationRow> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("You do not have permission to perform this action")))
                .flatMapMany { x -> applicationService.getAllByFilter(pageInfo.copy(pageInfo.filter.copy(status = Status.WaitingForRector))) }
    }

    fun getCountByFilter(tokenBody: TokenData, pageInfo: PageInfo<ApplicationRow>): Mono<Long> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("You do not have permission to perform this action")))
                .flatMap { x -> applicationService.getCountByFilter(pageInfo.copy(pageInfo.filter.copy(status = Status.WaitingForRector))) }
    }

    fun approveApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationFullService.getFullApplication(body.application.id!!) }
                .flatMap { x -> validateApproveAndSaveApplication(x, body) }
    }

    private fun validateApproveAndSaveApplication(dbApplicationInfo: ApplicationInfo?, reqApplicationInfo: ApplicationInfo): Mono<out Application>? {
        comparisonService.compareApplicationsInfo(dbApplicationInfo!!,reqApplicationInfo,role)
        return applicationService.updateApplication(reqApplicationInfo.application)
    }

    fun getFullApplication(tokenBody: TokenData, id: Long): Mono<ApplicationInfo> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role).switchIfEmpty(Mono.error(UnauthorizedException("")))
                .flatMap { x -> applicationFullService.getFullApplication(id) }
    }

}