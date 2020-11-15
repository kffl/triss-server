package com.pp.trisscore.model.architecture

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.classes.*

data class ApplicationInfo (
        val employee: Employee,
        val institute: Institute,
        val place: Place,
        val application: Application,
        val financialSource: FinancialSource?,
        val transport: List<Transport>,
        val advanceApplication: AdvanceApplication,
        val advancePayments: AdvancePaymentsInfo,
        val identityDocument : IdentityDocument)






































