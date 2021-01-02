package com.pp.trisscore.unit

import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfo
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.data.TestData.Companion.correctAdvanceApplication
import com.pp.trisscore.data.TestData.Companion.correctApplication
import com.pp.trisscore.data.TestData.Companion.correctApplicationForDirector
import com.pp.trisscore.data.TestData.Companion.correctFinancialSource
import com.pp.trisscore.data.TestData.Companion.correctFinancialSourceRectorAndWilda
import com.pp.trisscore.data.TestData.Companion.correctInstitute
import com.pp.trisscore.data.TestData.Companion.correctPlace
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfo
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfoForDirector
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfoForWaitingForWilda
import com.pp.trisscore.data.TestData.Companion.existingUserEmployee
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.model.enums.*
import com.pp.trisscore.service.ValidationService
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate
import java.util.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class ValidationServiceTest {

    private val validationService = ValidationService()

    //validateCreateApplicationInfo tests
    @Test
    fun shouldNotValidateCreateApplicationInfoNullEmployeeType(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateCreateApplicationInfo(exampleApplicationInfo, existingUserEmployee.copy(employeeType = null))
        }
        assertEquals("This user has no Role", result.message)
    }

    @Test
    fun shouldNotValidateCreateApplicationInfoFinancialSourceNotNull(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateCreateApplicationInfo(exampleApplicationInfo.copy(financialSource = correctFinancialSource), existingUserEmployee)
        }
        assertEquals("FinancialSource must be null", result.message)
    }

    @Test
    fun shouldNotValidateCreateApplicationInfoAbroadStartDateAfterAbroadEndDate(){
        val result = assertThrows<WrongDateException> {
            validationService.validateCreateApplicationInfo(
                    exampleApplicationInfo.copy(application = correctApplication.copy(
                            abroadStartDate = LocalDate.of(2020,12,16))), existingUserEmployee)
        }
        assertEquals("AbroadStartDate is after abroadEndDate.", result.message)
    }
    @Test
    fun shouldNotThrow() {
        assertDoesNotThrow { validationService.validateDates(exampleApplicationInfo) }
    }

    @Test
    fun shouldNotValidateCreateApplicationInfoCityIsNull(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateCreateApplicationInfo(
                    exampleApplicationInfo.copy(place = correctPlace.copy(city = "")), existingUserEmployee)
        }
        assertEquals("City cannot be null", result.message)
    }
    @Test
    fun shouldThrowWrongDateException_abroadStartInsuranceDateIsBeforeAbroadStartDate() {
        assertThrows(WrongDateException::class.java) {
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        application = exampleApplicationInfo.application
                            .copy(
                                abroadStartDateInsurance = exampleApplicationInfo.application.abroadStartDate.minusDays(
                                    1
                                )
                            )
                    )
            )
        }
    }

    @Test
    fun shouldNotValidateCreateApplicationInfoNotNullAdvanceApplicationId(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateCreateApplicationInfo(
                    exampleApplicationInfo.copy(advanceApplication = correctAdvanceApplication.copy(id = 1)), existingUserEmployee)
        }
        assertEquals("AdvanceApplicationId must be null", result.message)
    }
    @Test
    fun shouldThrowWrongDateException_abroadEndInsuranceDateIsAfterAbroadEndDate() {
        assertThrows(WrongDateException::class.java) {
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        application = exampleApplicationInfo.application
                            .copy(
                                abroadEndDateInsurance = exampleApplicationInfo.application.abroadEndDate.plusDays(
                                    1
                                )
                            )
                    )
            )
        }
    }

    @Test
    fun shouldNotValidateCreateApplicationInfoInstituteNameBlank(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateCreateApplicationInfo(
                    exampleApplicationInfo.copy(institute = correctInstitute.copy(name = "")), existingUserEmployee)
        }
        assertEquals("Institute name cannot be Blank", result.message)
    }
    @Test
    fun shouldThrowWrongDateException_conferenceStartDateIsBeforeAbroadStartDate() {
        assertThrows(WrongDateException::class.java) {
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        application = exampleApplicationInfo.application
                            .copy(
                                conferenceStartDate = exampleApplicationInfo.application.abroadStartDate.minusDays(
                                    1
                                )
                            )
                    )
            )
        }
    }

    //validateApproveApplicationInfo tests
    @Test
    fun shouldNotValidateApproveApplicationInfoWrongRole(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateApproveApplicationInfo(exampleApplicationInfoForDirector, Role.USER)
        }
        assertEquals("User don't have access to this", result.message)
    }
    @Test
    fun shouldThrowWrongDateException_conferenceEndDateIsAfterAbroadEndDate() {
        assertThrows(WrongDateException::class.java) {
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        application = exampleApplicationInfo.application
                            .copy(
                                conferenceEndDate = exampleApplicationInfo.application.abroadEndDate.plusDays(
                                    1
                                )
                            )
                    )
            )
        }
    }

    @Test
    fun shouldNotValidateApproveApplicationInfoWrongStatus(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateApproveApplicationInfo(exampleApplicationInfoForDirector.copy(
                    application = correctApplication.copy(status = StatusEnum.WaitingForRector.value)), Role.DIRECTOR )
        }
        assertEquals("Status must be WaitingForDirector", result.message)
    }
    @Test
    fun shouldThrowWrongDateException_departureDayIsBeforeAbroadStartDate() {
        assertThrows(WrongDateException::class.java) {
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        transport = Arrays.asList(
                            exampleApplicationInfo.transport.get(0)
                                .copy(departureDay = exampleApplicationInfo.application.abroadStartDate.minusDays(1))
                        )
                    )
            )
        }
    }

    @Test
    fun shouldNotValidateApproveApplicationInfoNotNullFinancialSourceId(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateApproveApplicationInfo(exampleApplicationInfoForDirector.copy(
                    application = correctApplicationForDirector.copy(financialSourceId = 1)), Role.DIRECTOR)
        }
        assertEquals("FinancialSourceId must be null", result.message)
    }

    @Test
    fun shouldNotValidateApproveApplicationInfoFinancialSourceEmpty(){
        val result = assertThrows<InvalidRequestBodyException> {
            validationService.validateApproveApplicationInfo(exampleApplicationInfoForWaitingForWilda.copy(
                    financialSource = correctFinancialSourceRectorAndWilda.copy(financialSource = "")
            ), Role.WILDA)
        }
        assertEquals("FinancialSourceSource cannot be empty", result.message)
    }
    @Test
    fun shouldThrowWrongDateException_departureDayIsAfterAbroadEndDate() {
        assertThrows(WrongDateException::class.java) {
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        transport = Arrays.asList(
                            exampleApplicationInfo.transport.get(0)
                                .copy(departureDay = exampleApplicationInfo.application.abroadEndDate.plusDays(1))
                        )
                    )
            )
        }
    }
    @Test
    fun shouldThrowWrongDateException_Inusurence_StartDateIsLessThan5DaysBeforeStart() {
        assertThrows(WrongDateException::class.java) {
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        application = exampleApplicationInfo.application
                            .copy(
                                abroadStartDate = LocalDate.now().plusDays(4),
                                abroadStartDateInsurance = LocalDate.now().plusDays(4)
                            )
                    )
            )
        }
    }
    @Test
    fun shouldNotThrowWrongDateException_Inusurence() {
        assertDoesNotThrow{
            validationService.validateDates(
                exampleApplicationInfo
                    .copy(
                        application = exampleApplicationInfo.application
                            .copy(
                                abroadStartDate = LocalDate.now().plusDays(5),
                                abroadStartDateInsurance = LocalDate.now().plusDays(5)
                            )
                    )
            )
        }
    }


}
