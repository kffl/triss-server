package com.pp.trisscore.model.applicationinfoelements

import java.math.BigDecimal
import java.time.LocalDate

data class AdvancePaymentRequestInfo(
        val requestPaymentDestination : String,
        val requestPaymentStartDate: LocalDate,
        val requestPaymentEndDate: LocalDate,
        val requestPaymentDays: Int,
        val requestPaymentDaysAmount: BigDecimal,
        val requestPaymentAccommodation: Int,
        val requestPaymentAccommodationLimit: BigDecimal,
        val requestPaymentTravelDiet: BigDecimal,
        val requestPaymentLocalTransportCosts: BigDecimal,
        val requestPaymentOtherExpensesDescription: String,
        val requestPaymentOtherExpensesValue: BigDecimal,
        val requestPaymentDaysAmountSum: BigDecimal,
        val requestPaymentAccommodationSum: BigDecimal,
        val requestPaymentSummarizedCosts: BigDecimal
)