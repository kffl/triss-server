package com.pp.trisscore.model.applicationinfoelements

import java.math.BigDecimal

data class AdvancePaymentsInfo (
        val conferenceFeeValue : BigDecimal,
        val conferenceFeePaymentTypeSelect : Long,
        val accommodationFeeValue : BigDecimal,
        val accommodationFeePaymentTypeSelect : Long
)
