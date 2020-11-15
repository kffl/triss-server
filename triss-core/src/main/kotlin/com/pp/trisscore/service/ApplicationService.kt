package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.GetFullApplicationRequestBody
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.Place
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
        val identityDocumentService: IdentityDocumentService,
        val instituteService: InstituteService) {

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationRowRepository.getAllByFilter(pageInfo)
    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) = applicationRowRepository.getCountByFilter(pageInfo)


    @Transactional
    fun createApplication(applicationInfo: ApplicationInfo): Mono<Transport> {
        validateApplication(applicationInfo)
        //TODO SHOULD BE USERID IN TOKEN
        val userId: Long = 1
        val institute = instituteService.getInstitute(applicationInfo.institute.name)
        val documentId = identityDocumentService.getIdentityDocument(applicationInfo.identityDocument.copy(employeeID = userId))
                .map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("DocumentId")))
        val prepaymentId = prepaymentService.createPrepayment(applicationInfo.advancePayments)
                .map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PrepaymentId")))
        val placeId = placeService.getPlace(applicationInfo.place).map { x -> x.id }
                .switchIfEmpty(Mono.error(ObjectNotFoundException("PlaceId")))
        val advanceApplication = placeId.flatMap { x -> advanceApplicationService.createAdvanceApplication(applicationInfo.advanceApplication, x!!) }
        val application = Mono.zip(documentId, prepaymentId, advanceApplication, institute)
                .flatMap { data ->
                    applicationRepository.save(fillApplication(applicationInfo, userId, data.t3.placeId!!, data.t1!!, data.t2!!, data.t3.id!!, data.t4.id!!))
                }
        return application.flatMap { x -> transportService.save(applicationInfo.transport, x.id!!) }
    }

    private fun fillApplication(applicationInfo: ApplicationInfo, userId: Long, placeId: Long, documentId: Long, prepaymentId: Long, advanceApplicationId: Long, instituteId: Long): Application {
        return applicationInfo.application.copy(createdOn = LocalDate.now(),employeeId = userId,placeId = placeId,identityDocumentID = documentId,
                prepaymentId = prepaymentId,advanceRequestId = advanceApplicationId,instituteId = instituteId,status = Status.WaitingForDirector)
    }

    fun validateApplication(applicationInfo: ApplicationInfo) {
        checkStartDateBeforeEndDate(applicationInfo.application.abroadStartDate,
                applicationInfo.application.abroadEndDate, "AbroadStartDate is after abroadEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.application.conferenceStartDate,
                applicationInfo.application.conferenceEndDate, "ConferenceStartDate is after conferenceEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.advanceApplication.startDate,
                applicationInfo.advanceApplication.endDate, "RequestPaymentStartDate is after RequestPaymentEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.application.abroadStartDateInsurance,
                applicationInfo.application.abroadEndDateInsurance, "AbroadStartDateInsurance is after abroadEndDateInsurance")
    }
    private fun checkStartDateBeforeEndDate(startDate: LocalDate, endDate: LocalDate, message: String) {
        if (startDate.isAfter(endDate)) {
            throw(WrongDateException(message))
        }
    }
}
