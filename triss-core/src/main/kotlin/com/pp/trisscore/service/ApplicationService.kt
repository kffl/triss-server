package com.pp.trisscore.service

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.WrongDateException
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
        val instituteService: InstituteService) {

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationRowRepository.getAllByFilter(pageInfo)
    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) = applicationRowRepository.getCountByFilter(pageInfo)


    @Transactional
    fun createApplication(applicationInfo: ApplicationInfo, user: Employee?): Mono<Transport> {
        if (user == null)
            throw EmployeeNotFoundException("User Not Found")
        validateApplication(applicationInfo,user)
        val institute = instituteService.getInstitute(applicationInfo.institute.name)
        val prepaymentId = prepaymentService.createPrepayment(applicationInfo.advancePayments)
                .map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PrepaymentId")))
        val placeId = placeService.getPlace(applicationInfo.place).map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PlaceId")))
        val advanceApplication = placeId.flatMap { x -> advanceApplicationService.createAdvanceApplication(applicationInfo.advanceApplication, x!!) }
        val application = Mono.zip(prepaymentId, advanceApplication, institute)
                .flatMap { data ->
                    applicationRepository.save(fillApplication(applicationInfo, user.id!!, data.t2.placeId!!, data.t1!!, data.t2.id!!, data.t3.id!!))
                }
        return application.flatMap { x -> transportService.save(applicationInfo.transport, x.id!!) }
    }

    private fun fillApplication(applicationInfo: ApplicationInfo, userId: Long, placeId: Long, prepaymentId: Long, advanceApplicationId: Long, instituteId: Long): Application {
        return applicationInfo.application.copy(createdOn = LocalDate.now(), employeeId = userId, placeId = placeId,
                prepaymentId = prepaymentId, advanceApplicationId = advanceApplicationId, instituteId = instituteId, status = Status.WaitingForDirector)
    }

    fun validateApplication(applicationInfo: ApplicationInfo, user: Employee) {
        checkStartDateBeforeEndDate(applicationInfo.application.abroadStartDate,
                applicationInfo.application.abroadEndDate, "AbroadStartDate is after abroadEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.application.conferenceStartDate,
                applicationInfo.application.conferenceEndDate, "ConferenceStartDate is after conferenceEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.advanceApplication.startDate,
                applicationInfo.advanceApplication.endDate, "RequestPaymentStartDate is after RequestPaymentEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.application.abroadStartDateInsurance,
                applicationInfo.application.abroadEndDateInsurance, "AbroadStartDateInsurance is after abroadEndDateInsurance")
        if (applicationInfo.application.firstName != user.firstName)
            throw(InvalidRequestBodyException("FirstName is not equal first name in database"))
        if (applicationInfo.application.surname != user.surname)
            throw(InvalidRequestBodyException("Surname is not equal surname in database"))
        if(applicationInfo.application.directorComments!=null)
            throw(InvalidRequestBodyException("Director comments must be null"))
        if(applicationInfo.application.rectorComments!=null)
            throw(InvalidRequestBodyException("Rector comments must be null"))
        if(applicationInfo.application.wildaComments!=null)
            throw(InvalidRequestBodyException("Wilda comments must be null"))
        if(applicationInfo.financialSource!=null)
            throw(InvalidRequestBodyException("FinancialSource must be null"))
        if(applicationInfo.application.id!=null)
            throw(InvalidRequestBodyException("Application Id must be null"))












        //TODO więcej walidacji do zrobienia
    }

    private fun checkStartDateBeforeEndDate(startDate: LocalDate, endDate: LocalDate, message: String) {
        if (startDate.isAfter(endDate)) {
            throw(WrongDateException(message))
        }
    }
}
