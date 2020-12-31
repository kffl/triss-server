package com.pp.trisscore.unit

import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfo
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.service.ValidationService
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate
import java.util.*

class ValidationServiceTest {

    private val validationService = ValidationService()

    @Test
    fun shouldNotThrow() {
        assertDoesNotThrow { validationService.validateDates(exampleApplicationInfo) }
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
