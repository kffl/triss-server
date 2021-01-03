package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.SettlementInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.classes.SettlementApplication
import com.pp.trisscore.model.classes.SettlementElement
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.model.rows.SettlementApplicationRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate


@Service
class UserService(
    private val employeeService: EmployeeService,
    private val applicationService: ApplicationService,
    private val advanceApplicationService: AdvanceApplicationService,
    private val applicationFullService: ApplicationFullService,
    private val validationService: ValidationService,
    private val instituteService: InstituteService,
    private val prepaymentService: PrepaymentService,
    private val placeService: PlaceService,
    private val transportService: TransportService,
    private val documentTypeService: DocumentTypeService,
    private val settlementApplicationService: SettlementApplicationService,
    private val settlementElementService: SettlementElementService,
    private val fullSettlementService: FullSettlementService
) {

    fun getMyApplications(tokenData: TokenData, body: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        if (tokenData.employeeId != body.filter.employeeId)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        return employeeService.findEmployeeOrCreateNewOne(tokenData)
            .flatMapMany { x -> applicationService.getAllByFilter(body) }
    }

    fun getCountByFilter(tokenData: TokenData, body: PageInfo<ApplicationRow>): Mono<Long> {
        if (tokenData.employeeId != body.filter.employeeId && body.filter.employeeId != null)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        val searchBody = body.copy(filter = body.filter.copy(employeeId = tokenData.employeeId))
        return employeeService.findEmployeeOrCreateNewOne(tokenData)
            .flatMap { x -> applicationService.getCountByFilter(searchBody) }
    }

    fun getMyFullApplication(tokenData: TokenData, body: Long): Mono<ApplicationInfo> {
        return applicationFullService.getFullUserApplication(body, tokenData)
    }

    fun createApplication(tokenData: TokenData, body: ApplicationInfo): Mono<Transport> {
        return employeeService.findEmployee(tokenData)
            .flatMap { x -> createApplication2(body, x) }
    }


    fun createApplication2(applicationInfo: ApplicationInfo, user: Employee): Mono<Transport> {
        validationService.validateCreateApplicationInfo(applicationInfo, user)
        val institute = instituteService.findInstituteByName(applicationInfo.institute.name)
        val prepaymentId = prepaymentService.createPrepayment(applicationInfo.advancePayments).map { x -> x.id }
            .switchIfEmpty(Mono.error(ObjectNotFoundException("PrepaymentId")))
        val placeId = placeService.getPlace(applicationInfo.place).map { x -> x.id }
            .switchIfEmpty(Mono.error(ObjectNotFoundException("PlaceId")))
        val documentType = documentTypeService.findById(applicationInfo.application.identityDocumentType)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("DocumentType")))
        val advanceApplication = placeId.flatMap { x ->
            advanceApplicationService.createAdvanceApplication(
                applicationInfo.advanceApplication,
                x!!
            )
        }
        val application = Mono.zip(prepaymentId, advanceApplication, institute, documentType)
            .flatMap { data ->
                applicationService.saveApplication(
                    applicationService.fillApplication(
                        applicationInfo,
                        user.employeeId!!,
                        data.t2.placeId!!,
                        data.t1!!,
                        data.t2.id!!,
                        data.t3.id!!
                    )
                )
            }
        return application.flatMap { x -> transportService.saveAll(applicationInfo.transport, x.id!!) }
    }

    @Transactional
    fun createSettlementApplication(tokenData: TokenData, body: Long): Mono<SettlementApplication> =
        employeeService.findEmployee(tokenData)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("User")))
            .flatMap { user ->
                applicationService.findById(body)
                    .switchIfEmpty(Mono.error(ObjectNotFoundException("Settlement Application")))
                    .flatMap { application ->
                        validationService.validateCreateSettlementApplication(application, user)
                        settlementApplicationService.createNewSettlement(application, user)
                    }
            }

    @Transactional
    fun sendToWildaSettlementApplication(tokenData: TokenData, body: Long): Mono<SettlementApplication> =
        employeeService.findEmployee(tokenData)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("User")))
            .flatMap { user ->
                settlementApplicationService.findById(body)
                    .switchIfEmpty(Mono.error(ObjectNotFoundException("Settlement Application")))
                    .flatMap { settlement ->
                        validationService.validateSettlementSendToWilda(settlement, user)
                        settlementApplicationService.saveSettlement(
                            settlement.copy(
                                lastModifiedDate = LocalDate.now(),
                                status = StatusEnum.WaitingForWilda.value
                            )
                        )
                    }
            }


    @Transactional
    fun addSettlementElement(tokenData: TokenData, body: SettlementElement): Mono<SettlementApplication> =
        employeeService.findEmployee(tokenData)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("User")))
            .flatMap { user ->
                settlementApplicationService.findById(body.settlementApplicationId)
                    .switchIfEmpty(Mono.error(ObjectNotFoundException("Settlement Application")))
                    .flatMap { settlement ->
                        validationService.validateCreateSettlementElement(body, user, settlement)
                        settlementElementService.addNewElement(body)
                            .flatMap { settlementApplicationService.saveSettlement(settlement.copy(lastModifiedDate = LocalDate.now())) }
                    }
            }

    @Transactional
    fun deleteSettlementElement(tokenData: TokenData, body: SettlementElement): Mono<Void> =
        employeeService.findEmployee(tokenData)
            .flatMap { user ->
                settlementElementService.findBy(body.settlementApplicationId, body.id!!, user.employeeId!!)
                    .flatMap { fullElement ->
                        validationService.validateDeleteSettlementElement(fullElement, body)
                        settlementElementService.deleteById(fullElement.settlementElementId)
                    }
            }

    fun getSettlementApplication(tokenData: TokenData, id: Long): Mono<SettlementInfo> =
        employeeService.findEmployee(tokenData)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("User")))
            .flatMap { user ->
                fullSettlementService.findSettlementInfoByIdAndEmployeeId(id, user.employeeId!!)
            }
            .switchIfEmpty(Mono.error(ObjectNotFoundException("Settlement")))

    fun getSettlementApplications(
        pageInfo: PageInfo<SettlementApplicationRow>,
        tokenBody: TokenData
    ): Flux<SettlementApplicationRow> = employeeService.findEmployee(tokenBody)
        .switchIfEmpty(Mono.error(ObjectNotFoundException("User")))
        .flatMapMany { x -> settlementApplicationService.getAllByFilter(pageInfo.copy(pageInfo.filter.copy(employeeId = x.employeeId))) }


}



