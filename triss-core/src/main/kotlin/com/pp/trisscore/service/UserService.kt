package com.pp.trisscore.service

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class UserService(private val employeeService: EmployeeService,
                  private val applicationService: ApplicationService,
                  private val advanceApplicationService: AdvanceApplicationService,
                  private val applicationFullService: ApplicationFullService,
                  private val validationService: ValidationService,
                  private val instituteService: InstituteService,
                  private val prepaymentService: PrepaymentService,
                  private val placeService: PlaceService,
                  private val transportService: TransportService) {

    fun getMyApplications(tokenData: TokenData, body: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        if (tokenData.employeeId != body.filter.employeeId)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        return employeeService.findEmployee(tokenData)
                .flatMapMany { x -> applicationService.getAllByFilter(body) }
    }

    fun getCountByFilter(tokenData: TokenData, body: PageInfo<ApplicationRow>): Mono<Long> {
        if (tokenData.employeeId != body.filter.employeeId && body.filter.employeeId != null)
            throw InvalidRequestBodyException("Wrong EmployeeId.")
        val searchBody = body.copy(filter = body.filter.copy(employeeId = tokenData.employeeId))
        return employeeService.findEmployee(tokenData)
                .flatMap { x -> applicationService.getCountByFilter(searchBody) }
    }

    fun getMyFullApplication(tokenData: TokenData, body: Long): Mono<ApplicationInfo> {
        return applicationFullService.getFullUserApplication(body, tokenData)
    }

    fun createApplication(tokenData: TokenData, body: ApplicationInfo): Mono<Transport> {
        return employeeService.findEmployee(tokenData)
                .flatMap { x -> createApplication2(body, x) }
    }

    @Transactional
    fun createApplication2(applicationInfo: ApplicationInfo, user: Employee?): Mono<Transport> {
        if (user == null)
            throw EmployeeNotFoundException("User Not Found")
        validationService.validateCreateApplicationInfo(applicationInfo, user)
        val institute = instituteService.findInstituteByName(applicationInfo.institute.name)
        val prepaymentId = prepaymentService.createPrepayment(applicationInfo.advancePayments)
                .map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PrepaymentId")))
        val placeId = placeService.getPlace(applicationInfo.place).map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PlaceId")))
        val advanceApplication = placeId.flatMap { x -> advanceApplicationService.createAdvanceApplication(applicationInfo.advanceApplication, x!!) }
        val application = Mono.zip(prepaymentId, advanceApplication, institute)
                .flatMap { data ->
                    applicationService.saveApplication(applicationService.fillApplication(applicationInfo, user.employeeId!!, data.t2.placeId!!, data.t1!!, data.t2.id!!, data.t3.id!!))
                }
        return application.flatMap { x -> transportService.save(applicationInfo.transport, x.id!!) }
    }


}
