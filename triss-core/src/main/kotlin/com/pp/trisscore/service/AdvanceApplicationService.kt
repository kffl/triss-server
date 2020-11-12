package com.pp.trisscore.service

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentRequestInfo
import com.pp.trisscore.model.classes.AdvanceApplication
import com.pp.trisscore.repository.AdvanceApplicationRepostiory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AdvanceApplicationService(val advanceApplicationRepostiory: AdvanceApplicationRepostiory) {
    fun createAdvanceApplication(advancePaymentRequest: AdvancePaymentRequestInfo, placeId:Long): Mono<AdvanceApplication> {
        val advanceApplication = AdvanceApplication(
                id =null,
                placeId = placeId,
                startDate = advancePaymentRequest.requestPaymentStartDate,
                endDate = advancePaymentRequest.requestPaymentEndDate,
                residenceDietQuantity = advancePaymentRequest.requestPaymentDays,
                residenceDietAmount = advancePaymentRequest.requestPaymentDaysAmount,
                accommodationQuantity = advancePaymentRequest.requestPaymentAccommodation,
                accommodationLimit = advancePaymentRequest.requestPaymentAccommodationLimit,
                travelDietAmount = advancePaymentRequest.requestPaymentTravelDiet,
                travelCosts = advancePaymentRequest.requestPaymentLocalTransportCosts,
                otherCostsDescription = advancePaymentRequest.requestPaymentOtherExpensesDescription,
                otherCostsAmount = advancePaymentRequest.requestPaymentOtherExpensesValue,
                advanceSum = advancePaymentRequest.requestPaymentSummarizedCosts
        )
        return advanceApplicationRepostiory.save(advanceApplication)
}



}