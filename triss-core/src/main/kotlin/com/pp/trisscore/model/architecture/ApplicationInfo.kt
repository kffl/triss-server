package com.pp.trisscore.model.architecture

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentRequestInfo
import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.applicationinfoelements.BasicInfo
import com.pp.trisscore.model.applicationinfoelements.InsuranceInfo
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.model.classes.IdentityDocument
import com.pp.trisscore.model.classes.Transport

data class ApplicationInfo (
        val basicInfo: BasicInfo,
        val transport: List<Transport>,
        val insurance : InsuranceInfo,
        val financialSource: FinancialSource?,
        val advancePaymentRequest: AdvancePaymentRequestInfo,
        val advancePayments: AdvancePaymentsInfo,
        val identityDocument : IdentityDocument,
        val comments: String?)






































