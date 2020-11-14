package com.pp.trisscore.model.applicationinfoelements

import com.pp.trisscore.model.enums.PaymentType
import java.math.BigDecimal

data class AdvancePaymentsInfo (
     val conferenceFeeValue : BigDecimal,
     val conferenceFeePaymentTypeSelect : PaymentType,
     val depositValue : BigDecimal,
     val depositPaymentTypeSelect : PaymentType
)