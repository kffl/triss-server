package com.pp.trisscore.service

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentRequestInfo
import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.applicationinfoelements.BasicInfo
import com.pp.trisscore.model.applicationinfoelements.InsuranceInfo
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.model.classes.IdentityDocument
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationFull
import com.pp.trisscore.repository.ApplicationFullRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ApplicationFullService(val applicationFullRepository: ApplicationFullRepository,
                             val transportService: TransportService) {

    fun findById(id: Long): Mono<ApplicationInfo> {
        val fullApp = applicationFullRepository.findById(id)
        val trans = transportService.findAllByApplicationID(id)
        return Mono.zip(fullApp, trans.collectList()).map { x -> getApplicationInfo(x.t1, x.t2) }
    }

    private fun getApplicationInfo(a: ApplicationFull, t: List<Transport>): ApplicationInfo {
        val basicInfo = BasicInfo(firstName = a.firstName,
                surname = a.surname,
                academicTitle = a.academicTitle,
                phoneNumber = a.phoneNumber,
                destinationCity = a.destinationCity,
                destinationCountry = a.destinationCountry,
                abroadStartDate = a.abroadStartDate,
                abroadEndDate = a.abroadEndDate,
                purpose = a.purpose,
                conference = a.conference,
                subject = a.subject,
                conferenceStartDate = a.conferenceStartDate,
                conferenceEndDate = a.conferenceEndDate
        )
        val insuranceInfo = InsuranceInfo(
                firstName = a.firstName,
                surname = a.surname,
                birthDate = a.birthDate,
                departureCountry = a.destinationCountry,
                abroadStartDateInsurance = a.abroadStartDate,
                abroadEndDateInsurance = a.abroadEndDate,
                selfInsuredCheckbox = a.selfInsuredCheckbox
        )
        val advancePaymentRequest = AdvancePaymentRequestInfo(
                requestPaymentDestination = a.destinationCity,
                requestPaymentStartDate = a.requestPaymentStartDate,
                requestPaymentEndDate = a.requestPaymentEndDate,
                requestPaymentDays = a.requestPaymentDays,
                requestPaymentDaysAmount = a.requestPaymentDaysAmount,
                requestPaymentAccommodation = a.requestPaymentAccommodation,
                requestPaymentAccommodationLimit = a.requestPaymentAccommodationLimit,
                requestPaymentTravelDiet = a.requestPaymentTravelDiet,
                requestPaymentLocalTransportCosts = a.requestPaymentLocalTransportCosts,
                requestPaymentOtherExpensesDescription = a.requestPaymentOtherExpensesDescription,
                requestPaymentOtherExpensesValue = a.requestPaymentOtherExpensesValue,
                requestPaymentDaysAmountSum = a.requestPaymentDaysAmountSum,
                requestPaymentAccommodationSum = a.requestPaymentAccommodationSum,
                requestPaymentSummarizedCosts = a.requestPaymentSummarizedCosts
        )
        val advancePaymentsInfo = AdvancePaymentsInfo(
                conferenceFeeValue = a.conferenceFeeValue,
                conferenceFeePaymentTypeSelect = a.conferenceFeePaymentTypeSelect,
                depositPaymentTypeSelect = a.depositPaymentTypeSelect,
                depositValue = a.depositValue
        )
        val identityDocument = IdentityDocument(
                id = a.identityID,
                type = a.type,
                number = a.number,
                employeeID = a.employeeID
        )
        return ApplicationInfo(basicInfo = basicInfo,
                transport = t,
                insurance = insuranceInfo,
                advancePaymentRequest = advancePaymentRequest,
                advancePayments = advancePaymentsInfo,
                identityDocument = identityDocument,
                comments = a.comments,
                financialSource = getFinancalSource(a)
        )
    }

    private fun getFinancalSource(a: ApplicationFull): FinancialSource? {
        if (a.financialSourceId == null)
            return null
        else
            return FinancialSource(
                    id = a.financialSourceId,
                    allocationAccount = a.allocationAccount,
                    MPK = a.MPK,
                    financialSource = a.financialSource,
                    project = a.project
            )
    }


}