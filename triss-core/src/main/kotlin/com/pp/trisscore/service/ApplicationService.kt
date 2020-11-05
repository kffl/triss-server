package com.pp.trisscore.service

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.Place
import com.pp.trisscore.model.enums.Status
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.repository.ApplicationRepository
import com.pp.trisscore.repository.ApplicationRowRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.sql.Date
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
        val financialSourceService: FinancialSourceService) {

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationRowRepository.getAllByFilter(pageInfo)
    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) = applicationRowRepository.getCountByFilter(pageInfo)


    @Transactional
    fun createApplication(applicationInfo: ApplicationInfo): Mono<Long?> {
        validateApplication(applicationInfo)
        //TODO SCHOULD BE USERID IN TOKEN
        val userId: Long = 1
        val documentId = identityDocumentService.getIdentityDocument(applicationInfo.identityDocument.copy(employeeID = userId)).map { x -> x.id }
        documentId.subscribe() {}
        val placeId = placeService.getPlace(Place(
                id = null,
                city = applicationInfo.basicInfo.destinationCity,
                country = applicationInfo.basicInfo.destinationCountry)).map { x -> x.id }
        val financialSourceId = financialSourceService.createFinancialSource(applicationInfo.financialSource).map { x -> x.id }
        val prepaymentId = prepaymentService.createPrepayment(applicationInfo.advancePayments).map { x -> x.id }
        val advanceApplicationId = advanceApplicationService.createAdvanceApplication(applicationInfo.advancePaymentRequest, placeId).map { y -> y.id }
        val application = fillApplication(applicationInfo, userId, documentId, placeId, financialSourceId, prepaymentId, advanceApplicationId)
        val applicationId = applicationRepository.save(application).map { x -> x.id }
        applicationInfo.transport.forEach { x ->
            transportService.createTransport(
                    x.copy(applicationID = applicationId))
        }

        return placeId
    }

    fun fillApplication(applicationInfo: ApplicationInfo,
                        userId: Long,
                        documentId: Mono<Long?>,
                        placeId: Mono<Long?>,
                        financialSourceId: Mono<Long?>,
                        prepaymentId: Mono<Long?>,
                        advanceApplicationId: Mono<Long?>): Application {
        return Application(id = null,
                employeeId = userId,
                createdOn = Date.valueOf(LocalDate.now()),
                placeId = placeId,
                abroadStartDate = applicationInfo.basicInfo.abroadStartDate,
                abroadEndDate = applicationInfo.basicInfo.abroadEndDate,
                purpose = applicationInfo.basicInfo.purpose,
                conference = applicationInfo.basicInfo.conference,
                subject = applicationInfo.basicInfo.subject,
                conferenceStartDate = applicationInfo.basicInfo.conferenceStartDate,
                conferenceEndDate = applicationInfo.basicInfo.conferenceEndDate,
                financialSourceId = financialSourceId,
                abroadStartDateInsurance = applicationInfo.insurance.abroadStartDateInsurance,
                abroadEndDateInsurance = applicationInfo.insurance.abroadEndDateInsurance,
                selfInsured = applicationInfo.insurance.selfInsuredCheckbox,
                advanceRequestId = advanceApplicationId,
                prepaymentId = prepaymentId,
                identityDocumentID = documentId,
                comments = applicationInfo.comments,
                status = Status.WaitingForDirector

        )
    }


    fun validateApplication(applicationInfo: ApplicationInfo) {
        //TODO
    }
}
