package com.pp.trisscore.unit

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.service.ValidationService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertDoesNotThrow

class ValidationServiceTest {

    private val goodFinancialSource = FinancialSource(null, "AllocationAccountNumberHere","MPK_1","Budżet PP","TRISS")
    private val validationService = ValidationService()

    @Test
    fun shouldNotValidateFinancialSourceAndThrowInvalidRequestBody() {
       assertThrows(InvalidRequestBodyException::class.java){
           validationService.validateFinancialSource(goodFinancialSource.copy(allocationAccount = null, mpk = null, financialSource = null, project = null))
       }
    }

    @Test
    fun shouldValidateFinancialSource(){
        assertDoesNotThrow("Should not throw an InvalidRequestBodyException"){
            validationService.validateFinancialSource(goodFinancialSource)
        }
    }
}
