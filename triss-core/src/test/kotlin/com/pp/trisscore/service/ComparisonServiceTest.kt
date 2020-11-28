package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.LocalDate

internal class ComparisonServiceTest() {
    private val comparisonService = ComparisonService()
    private val institute = Institute(0,"Instytut Informatyki", true)
    private val place = Place(0,"Poland","Warszawa")
    private val financialSource = FinancialSource(0, "AllocationAccountNumber", "MPK","Source","TRISS")
    private val transport = Transport(0, 0, "Poznań", "Tokio",
            LocalDate.parse("2020-11-30"),30,12, Vehicle.Plane, "RyanAir")
    private val advancePaymentsInfo = AdvancePaymentsInfo(1000.toBigDecimal(), PaymentType.Transfer, 2000.toBigDecimal(), PaymentType.Transfer)
    private val advanceApplication = AdvanceApplication(0, 0, LocalDate.parse("2020-11-28"),
            LocalDate.parse("2020-12-03"), 1, 1000.toBigDecimal(), 1, 1000.toBigDecimal(),
            800.toBigDecimal(), 600.toBigDecimal(), null, null, 1000.toBigDecimal(), 1000.toBigDecimal(),
            3400.toBigDecimal())
    private val application = Application(0, "Jan", "Kowalczyk", LocalDate.parse("1990-01-01"),
            "Prof.", "+48 123456789", 170387, DocumentType.IdCard,"ABC12345",
            LocalDate.parse("2020-11-03"), 1, LocalDate.parse("2020-12-12"), LocalDate.parse("2020-12-15"),
            0,"Konferencja", "AntyCovid2020", "TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą",
            LocalDate.parse("2020-12-13"), LocalDate.parse("2020-12-14"), null, LocalDate.parse("2020-12-12"),
            LocalDate.parse("2020-12-15"), false, 1, 1, null, null,
            null, null, Status.WaitingForDirector)

    @Test
    fun shouldCompareInstitutesAndThrowInvalidRequestBody() {
        assertThrows(InvalidRequestBodyException::class.java){
            comparisonService.compareInstitutes(institute, institute.copy(name = "Instytut Matematyki"))
        }
    }
    @Test
    fun shouldCompareInstitutesAndNotThrow(){
        assertDoesNotThrow("Should not throw an InvalidRequestBodyException"){
            comparisonService.compareInstitutes(institute, institute)
        }
    }

    @Test
    fun shouldComparePlacesAndThrowInvalidRequestBody() {
        assertThrows(InvalidRequestBodyException::class.java){
            comparisonService.comparePlaces(place, place.copy(city = "Poznań"))
        }
    }
    @Test
    fun shouldComparePlacesAndNotThrow(){
        assertDoesNotThrow("Should not throw an InvalidRequestBodyException"){
            comparisonService.comparePlaces(place, place)
        }
    }

    @Test
    fun shouldCompareApplicationsAndThrow() {
        assertThrows(InvalidRequestBodyException::class.java){
            comparisonService.compareApplications(application, application.copy(status = Status.WaitingForDirector), Role.DIRECTOR)
        }
    }
    @Test
    fun shouldCompareApplicationsAndNotThrow(){
        assertDoesNotThrow("Shouldn't throw InvalidBodyRequestException"){
            comparisonService.compareApplications(application, application.copy(status = Status.WaitingForWilda), Role.DIRECTOR)
        }
    }

    @Test
    fun shouldCompareFinancialSourcesAndThrow() {
        assertThrows(InvalidRequestBodyException::class.java){
            comparisonService.compareFinancialSources(financialSource, financialSource.copy(mpk = null))
        }
    }
    @Test
    fun shouldCompareFinancialSourcesAndNotThrow(){
        assertDoesNotThrow("Shouldn't throw InvalidBodyRequestException"){
            comparisonService.compareFinancialSources(financialSource, financialSource)
        }
    }

    @Test
    fun shouldCompareTransportsAndThrow() {
        assertThrows(InvalidRequestBodyException::class.java){
            comparisonService.compareTransports(transport, transport.copy(carrier = "LOT"))
        }
    }
    @Test
    fun shouldCompareTransportsAndNotThrow(){
        assertDoesNotThrow("Shouldn't throw InvalidBodyRequestException"){
            comparisonService.compareTransports(transport, transport)
        }
    }

    @Test
    fun shouldCompareAdvanceApplicationAndThrow() {
        assertThrows(InvalidRequestBodyException::class.java){
            comparisonService.compareAdvanceApplication(advanceApplication, advanceApplication.copy(advanceSum = 5000.toBigDecimal()))
        }
    }
    @Test
    fun shouldCompareAdvanceApplicationAndNotThrow() {
        assertDoesNotThrow("Shouldn't throw InvalidBodyRequestException"){
            comparisonService.compareAdvanceApplication(advanceApplication, advanceApplication)
        }
    }

    @Test
    fun shouldCompareAdvancePaymentsAndThrow() {
        assertThrows(InvalidRequestBodyException::class.java){
            comparisonService.compareAdvancePayments(advancePaymentsInfo, advancePaymentsInfo.copy(conferenceFeePaymentTypeSelect = PaymentType.Cash))
        }
    }
    @Test
    fun shouldCompareAdvancePaymentsAndNotThrow() {
        assertDoesNotThrow("Shouldn't throw InvalidBodyRequestException"){
            comparisonService.compareAdvancePayments(advancePaymentsInfo, advancePaymentsInfo)
        }
    }
}