package com.pp.trisscore.data

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.*
import com.pp.trisscore.model.rows.ApplicationRow
import java.math.BigDecimal
import java.time.LocalDate

interface TestData {
    companion object {
        //Different types of user data
        val newEmployeeTokenData = TokenData(1999, "Marcel", "TOLEN")
        val newEmployee = Employee(id = null, employeeId = 1999, firstName = "Marcel", surname = "TOLEN", birthDate = LocalDate.now(), phoneNumber = "666666666", academicDegree = "BRAK", instituteID = 1, employeeType = Role.USER)
        val existingUserToken = TokenData(170387, "Jan", "Kowalczyk")
        val existingUserEmployee = Employee(id = 1, employeeId = 170387, firstName = "Jan", surname = "Kowalczyk",
                birthDate = LocalDate.of(2000, 1, 1), phoneNumber = "+48 123456789",
                academicDegree = "Prof.", instituteID = 1, employeeType = Role.USER)
        val existingDirectorToken = TokenData(167711, "Andrzej", "Nowak")
        val existingDirector = Employee(id = 4, employeeId = 167711, firstName = "Andrzej", surname = "Nowak", birthDate = LocalDate.of(1988, 7, 16), phoneNumber = "+48 987456321", academicDegree = "Prof.", instituteID = 1, employeeType = Role.DIRECTOR)

        //Different types of applications
        val correctApplicationForDirector = Application(id = 3, firstName = "Jan", surname = "Kowalczyk",
                birthDate = LocalDate.of(2000, 1, 1), academicDegree = "Prof.",
                phoneNumber = "+48 123456789", employeeId = 170387, identityDocumentType = DocumentType.Passport,
                identityDocumentNumber = "AB6789000", createdOn = LocalDate.of(2020, 12, 1), placeId = 5,
                abroadStartDate = LocalDate.of(2020, 12, 12), abroadEndDate = LocalDate.of(2020,12,15),
                instituteId = 1, purpose = "Konferencja", conference = "AntyCovid2020", subject = " TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą",
                conferenceStartDate = LocalDate.of(2020,12,13), conferenceEndDate = LocalDate.of(2020,12,14),
                financialSourceId = null, abroadStartDateInsurance = LocalDate.of(2020,12,12),
                abroadEndDateInsurance = LocalDate.of(2020,12,15), selfInsured = false,
                advanceApplicationId = 3, prepaymentId = 3, comments = "comments",
                wildaComments = null, directorComments = null, rectorComments = null, status = Status.WaitingForDirector)
        val correctInstitute = Institute(id = 1, name = "Instytut Architektury i Planowania Przestrzennego", active = true)
        val correctTransportListForDirector = listOf(
                Transport(id = 5, applicationID = 3, destinationFrom = "Poznań", destinationTo = "Berlin",
                        departureDay = LocalDate.of(2020, 12, 12), departureHour = 10, departureMinute = 10,
                        vehicleSelect = Vehicle.Plane, carrier = "RyanAir"),
                Transport(id = 6, applicationID = 3, destinationFrom = "Berlin", destinationTo = "Poznań",
                        departureDay = LocalDate.of(2020, 12, 15), departureHour = 12, departureMinute = 30,
                        vehicleSelect = Vehicle.Plane, carrier = "RyanAir"))
        val correctAdvanceApplicationForDirector = AdvanceApplication(id = 3, startDate = LocalDate.of(2020, 12, 12),
                endDate = LocalDate.of(2020, 12, 15), residenceDietAmount = BigDecimal(10).setScale(2),
                residenceDietQuantity = 10, residenceDietSum = BigDecimal(100).setScale(2), accommodationQuantity = 10,
                accommodationLimit = BigDecimal(10).setScale(2), accommodationSum = BigDecimal(100).setScale(2),
                otherCostsAmount = BigDecimal(100.00).setScale(2), otherCostsDescription = "OtherCosts", placeId = 5,
                travelCosts = BigDecimal(100).setScale(2), travelDietAmount = BigDecimal(100).setScale(2),
                advanceSum = BigDecimal(100 + 100 + 100 + 100).setScale(2))
        val correctAdvancePaymentsInfoForDirector = AdvancePaymentsInfo(conferenceFeeValue = BigDecimal.valueOf(100).setScale(2),
                conferenceFeePaymentTypeSelect = PaymentType.Blik, accommodationFeeValue = BigDecimal.valueOf(100).setScale(2),
                accommodationFeePaymentTypeSelect = PaymentType.Card)
        val correctPlaceForDirector = Place(id = 5, city = "Berlin", country = "Niemcy")
        val exampleApplicationInfoForDirector = ApplicationInfo(
                application = correctApplicationForDirector,
                institute = correctInstitute,
                financialSource = null,
                transport = correctTransportListForDirector,
                advanceApplication = correctAdvanceApplicationForDirector,
                advancePayments = correctAdvancePaymentsInfoForDirector,
                place = correctPlaceForDirector)



        val correctApplication = Application(id = null, firstName = "Jan", surname = "Kowalczyk", birthDate = LocalDate.of(2000, 1, 1)
                , academicDegree = "Prof.", phoneNumber = "+48 123456789", employeeId = 1, identityDocumentType = DocumentType.Passport,
                identityDocumentNumber = "identityDocumentNumber", createdOn = null, placeId = null,
                abroadStartDate = LocalDate.now().plusDays(10), abroadEndDate = LocalDate.now().plusDays(40),
                instituteId = 1, purpose = "prupose", conference = "conference", subject = "subject", conferenceStartDate = LocalDate.now().plusDays(11),
                conferenceEndDate = LocalDate.now().plusDays(39), financialSourceId = null, abroadStartDateInsurance = LocalDate.now().plusDays(10)
                , abroadEndDateInsurance = LocalDate.now().plusDays(40), selfInsured = true, advanceApplicationId = null, prepaymentId = null, comments = "blablabla",
                wildaComments = null, directorComments = null, rectorComments = null, status = Status.WaitingForDirector)
        val correctFinancialSource = FinancialSource(id = null, allocationAccount = "01 2345 6789 0123 4567 8901 2345", mpk = "MPK_1", financialSource = "Financial Source 1", project = "Project X")
        val correctTransportList = listOf(Transport(id = null, applicationID = null, destinationFrom = "Poznań", destinationTo = "Berlin", departureDay = LocalDate.now().plusDays(10), departureHour = 10, departureMinute = 10, vehicleSelect = Vehicle.Bus, carrier = "carrier"),
                Transport(id = null, applicationID = null, destinationFrom = "Berlin", destinationTo = "Poznań", departureDay = LocalDate.now().plusDays(10), departureHour = 10, departureMinute = 10, vehicleSelect = Vehicle.Bus, carrier = "carrier"))
        val correctAdvanceApplication = AdvanceApplication(id = null, startDate = LocalDate.now().plusDays(10), endDate = LocalDate.now().plusDays(40),
                residenceDietAmount = BigDecimal(10).setScale(2), residenceDietQuantity = 10, residenceDietSum = BigDecimal(100).setScale(2),
                accommodationQuantity = 10, accommodationLimit = BigDecimal(10).setScale(2), accommodationSum = BigDecimal(100).setScale(2),
                otherCostsAmount = BigDecimal(100.00).setScale(2), otherCostsDescription = "blablabla", placeId = null, travelCosts = BigDecimal(100).setScale(2),
                travelDietAmount = BigDecimal(100).setScale(2), advanceSum = BigDecimal(100 + 100 + 100 + 100).setScale(2))
        val correctAdvancePaymentsInfo = AdvancePaymentsInfo(conferenceFeeValue = BigDecimal.valueOf(100).setScale(2), conferenceFeePaymentTypeSelect = PaymentType.Blik,
                accommodationFeeValue = BigDecimal.valueOf(100).setScale(2), accommodationFeePaymentTypeSelect = PaymentType.Card)
        val correctPlace = Place(id = null, city = "Berlin", country = "Niemcy")
        val exampleApplicationInfo = ApplicationInfo(
                application = correctApplication,
                institute = correctInstitute,
                financialSource = null,
                transport = correctTransportList,
                advanceApplication = correctAdvanceApplication,
                advancePayments = correctAdvancePaymentsInfo,
                place = correctPlace)
        val filter = ApplicationRow(id = null, firstName = null, surname = null,
                employeeId = null, instituteName = null, instituteId = null, country = null, abroadEndDate = null, abroadStartDate = null, city = null, status = null)
        val pageInfo = PageInfo(filter = filter,
                desc = false, orderBy = "id", pageSize = 100, pageNumber = 0)
        fun getExampleForUserApplication(user: Employee) = correctApplication.copy(firstName = user.firstName, surname = user.surname, birthDate = user.birthDate, academicDegree = user.academicDegree, phoneNumber = user.phoneNumber, employeeId = user.employeeId, instituteId = user.instituteID)
    }

}
