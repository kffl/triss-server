package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.SettlementInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.SettlementApplication
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class WildaService(
    private val employeeService: EmployeeService,
    private val applicationService: ApplicationService,
    private val applicationFullService: ApplicationFullService,
    private val comparisonService: ComparisonService,
    private val validationService: ValidationService,
    private val settlementApplicationService: SettlementApplicationService,
    private val settlementElementService: SettlementElementService,
    private val fullSettlementService: FullSettlementService
) {


    private val role = Role.WILDA

    fun getApplications(pageInfo: PageInfo<ApplicationRow>, tokenBody: TokenData): Flux<ApplicationRow> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
            .switchIfEmpty(Mono.error(UnauthorizedException("You do not have permission to perform this action")))
            .flatMapMany { x -> applicationService.getAllByFilter(pageInfo.copy(pageInfo.filter.copy(status = StatusEnum.WaitingForWilda.value))) }
    }

    fun getCountByFilter(tokenBody: TokenData, pageInfo: PageInfo<ApplicationRow>): Mono<Long> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
            .switchIfEmpty(Mono.error(UnauthorizedException("You do not have permission to perform this action")))
            .flatMap { x -> applicationService.getCountByFilter(pageInfo.copy(pageInfo.filter.copy(status = StatusEnum.WaitingForWilda.value))) }
    }

    fun approveApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
        validationService.validateApproveApplicationInfo(body, role)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
            .switchIfEmpty(
                Mono.error(
                    UnauthorizedException(
                        tokenBody.employeeId.toString(),
                        tokenBody.firstname,
                        tokenBody.surname
                    )
                )
            )
            .flatMap { applicationFullService.getFullApplication(body.application.id!!) }
            .switchIfEmpty(Mono.error(ObjectNotFoundException("Application")))
            .flatMap { x -> validateApproveAndSaveApplication(x, body) }
    }

    private fun validateApproveAndSaveApplication(
        dbApplicationInfo: ApplicationInfo,
        reqApplicationInfo: ApplicationInfo
    ): Mono<Application>? {
        comparisonService.compareApplicationsInfo(dbApplicationInfo, reqApplicationInfo, role)
        return applicationService.saveApplication(reqApplicationInfo.application.copy(status = StatusEnum.WaitingForRector.value))
    }

    fun getFullApplication(tokenBody: TokenData, id: Long): Mono<ApplicationInfo> {
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
            .switchIfEmpty(
                Mono.error(
                    UnauthorizedException(
                        tokenBody.employeeId.toString(),
                        tokenBody.firstname,
                        tokenBody.surname
                    )
                )
            )
            .flatMap { x -> applicationFullService.getFullApplication(id) }
    }

    @Transactional
    fun rejectApplication(tokenBody: TokenData, body: ApplicationInfo): Mono<Application> {
        validationService.validateRejectApplicationInfo(body, role)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
            .switchIfEmpty(
                Mono.error(
                    UnauthorizedException(
                        tokenBody.employeeId.toString(),
                        tokenBody.firstname,
                        tokenBody.surname
                    )
                )
            )
            .flatMap { x -> applicationFullService.getFullDirectorApplication(body.application.id!!, x!!) }
            .switchIfEmpty(Mono.error(ObjectNotFoundException("Application")))
            .flatMap { x -> validateRejectAndSaveApplication(x, body) }
    }

    private fun validateRejectAndSaveApplication(
        dbApplication: ApplicationInfo,
        reqApplication: ApplicationInfo
    ): Mono<Application> {
        comparisonService.compareApplicationsInfo(dbApplication, reqApplication, role)
        return applicationService.saveApplication(reqApplication.application.copy(status = StatusEnum.RejectedByWilda.value))
    }

    fun getSettlementApplication(tokenData: TokenData, id: Long): Mono<SettlementInfo> =
        employeeService.findEmployeeAndCheckRole(tokenData, role)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("User")))
            .flatMap {
                fullSettlementService.findSettlementInfoById(id)
            }

    fun changeSettlementApplicationStatus(
        tokenBody: TokenData,
        settlementInfo: SettlementInfo,
        status: Long
    ): Mono<SettlementApplication> {
        validationService.validateChangingSettlementApplicationStatus(settlementInfo,status)
        return employeeService.findEmployeeAndCheckRole(tokenBody, role)
            .switchIfEmpty(Mono.error(UnauthorizedException(tokenBody.employeeId.toString(), tokenBody.firstname, tokenBody.surname)))
            .flatMap { fullSettlementService.findSettlementInfoById(settlementInfo.fullSettlementApplication.id!!) }
            .switchIfEmpty(Mono.error(ObjectNotFoundException("SettlementApplication")))
            .flatMap { x -> compareSettlementAndChangeStatus(x, settlementInfo, status) }
    }

    private fun compareSettlementAndChangeStatus(
        dbSettlFull: SettlementInfo,
        reqSettlFull: SettlementInfo,
        status: Long
    ): Mono<SettlementApplication> {
        comparisonService.compareSettlementChangeStatus(dbSettlFull,reqSettlFull)
        val dbsettl = dbSettlFull.fullSettlementApplication;
        return settlementApplicationService.saveSettlement(
            SettlementApplication(
                dbsettl.id,
                dbsettl.applicationId,
                dbsettl.creatorId,
                status,
                dbsettl.lastModifiedDate,
                reqSettlFull.fullSettlementApplication.wildaComments
            )
        )
    }


}
