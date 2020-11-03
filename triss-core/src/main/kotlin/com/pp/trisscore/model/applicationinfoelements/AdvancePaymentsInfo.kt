package com.pp.trisscore.model.applicationinfoelements

import java.math.BigDecimal

data class AdvancePaymentsInfo (
     val conferenceFeeValue : BigDecimal,
     val conferenceFeePaymentTypeSelect : Int,
     val depositValue : BigDecimal,
     val depositPaymentTypeSelect : Int

)