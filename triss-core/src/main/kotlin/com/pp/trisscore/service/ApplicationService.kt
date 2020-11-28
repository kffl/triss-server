package com.pp.trisscore.service

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.enums.Status
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.repository.ApplicationRepository
import com.pp.trisscore.repository.ApplicationRowRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/

@Service
class ApplicationService(
        val applicationRowRepository: ApplicationRowRepository,
        val applicationRepository: ApplicationRepository,
        val advanceApplicationService: AdvanceApplicationService,
        val transportService: TransportService,
        val placeService: PlaceService,
        val prepaymentService: PrepaymentService,
        val instituteService: InstituteService,
        val validationService: ValidationService) {

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationRowRepository.getAllByFilter(pageInfo)
    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) = applicationRowRepository.getCountByFilter(pageInfo)

    @Transactional
    fun createApplication(applicationInfo: ApplicationInfo, user: Employee?): Mono<Transport> {
        if (user == null)
            throw EmployeeNotFoundException("User Not Found")
        validationService.validateUserApplication(applicationInfo, user)
        val institute = instituteService.getInstitute(applicationInfo.institute.name)
        val prepaymentId = prepaymentService.createPrepayment(applicationInfo.advancePayments)
                .map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PrepaymentId")))
        val placeId = placeService.getPlace(applicationInfo.place).map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PlaceId")))
        val advanceApplication = placeId.flatMap { x -> advanceApplicationService.createAdvanceApplication(applicationInfo.advanceApplication, x!!) }
        val application = Mono.zip(prepaymentId, advanceApplication, institute)
                .flatMap { data ->
                    applicationRepository.save(fillApplication(applicationInfo, user.eLoginId!!, data.t2.placeId!!, data.t1!!, data.t2.id!!, data.t3.id!!))
                }
        return application.flatMap { x -> transportService.save(applicationInfo.transport, x.id!!) }
    }


    fun saveApplication(application: Application): Mono<Application> = applicationRepository.save(application)

    fun fillApplication(applicationInfo: ApplicationInfo, userId: Long, placeId: Long, prepaymentId: Long, advanceApplicationId: Long, instituteId: Long): Application {
        return applicationInfo.application.copy(createdOn = LocalDate.now(), employeeId = userId, placeId = placeId,
                prepaymentId = prepaymentId, advanceApplicationId = advanceApplicationId, instituteId = instituteId, status = Status.WaitingForDirector)
    }
}
