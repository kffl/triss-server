package com.pp.trisscore.model.applicationinfoelements

import java.math.BigDecimal
import java.sql.Date

data class AdvancePaymentRequestInfo(
        val requestPaymentDestination : String,
        val requestPaymentStartDate: Date,
        val requestPaymentEndDate: Date,
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