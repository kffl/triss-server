package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.classes.FinancialSource
import org.springframework.stereotype.Service

@Service
class ValidationService {

    fun validateFinancialSource(financialSource: FinancialSource?) {
        if (financialSource == null)
            throw InvalidRequestBodyException("")
        if (financialSource.MPK != null)
            throw InvalidRequestBodyException("")
        if (financialSource.allocationAccount != null)
            throw InvalidRequestBodyException("")
        if (financialSource.financialSource != null)
            throw InvalidRequestBodyException("")
        if (financialSource.project != null)
            throw InvalidRequestBodyException("")
    }


}