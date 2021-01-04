package com.pp.trisscore.data

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.*
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
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
        val existingRectorToken = TokenData(3, "Jerzy", "Zbiałowierzy")
        val existingRector = Employee(id = 3, employeeId = 3, firstName = "Jerzy", surname = "Zbiałowierzy", birthDate = LocalDate.of(1980,5,15), phoneNumber = "+48 541236987", academicDegree = "Prof.", instituteID = 1, employeeType = Role.RECTOR)
        val existingWildaToken = TokenData(2, "Jan", "Kowalski")
        val existingWilda = Employee(id = 2, employeeId = 2, firstName = "Jan", surname = "Kowalski", birthDate = LocalDate.of(1990,3,21), phoneNumber = "+48 321456987", academicDegree = "Prof.", instituteID = 1, employeeType = Role.WILDA)


        val correctFinancialSource = FinancialSource(id = null, allocationAccount = "01 2345 6789 0123 4567 8901 2345", mpk = "MPK_1", financialSource = "Financial Source 1", project = "Project X")
        val correctFinancialSourceRectorAndWilda = FinancialSource(id = 1, allocationAccount = "01 2345 6789 0123 4567 8901 2345", mpk = "MPK_1", financialSource = "Financial Source 1", project = "Project X")
        //Different types of applications
        val correctApplicationForWaitingForWilda = Application(id = 5, firstName = "Jan", surname = "Kowalczyk",
                birthDate = LocalDate.of(2000, 1, 1), academicDegree = "Prof.",
                phoneNumber = "+48 123456789", employeeId = 170387, identityDocumentType = 2,
                identityDocumentNumber = "AB6789000", createdOn = LocalDate.of(2020, 12, 1), placeId = 5,
                abroadStartDate = LocalDate.now().plusDays(10), abroadEndDate = LocalDate.now().plusDays(15),
                instituteId = 1, purpose = "Konferencja", conference = "AntyCovid2020", subject = " TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą",
                conferenceStartDate = LocalDate.now().plusDays(11), conferenceEndDate = LocalDate.now().plusDays(14),
                financialSourceId = 1, abroadStartDateInsurance = LocalDate.now().plusDays(10),
                abroadEndDateInsurance = LocalDate.now().plusDays(15), selfInsured = false,
                advanceApplicationId = 3, prepaymentId = 3, comments = "User Comments",
                wildaComments = null, directorComments = "Director comments", rectorComments = null, status = StatusEnum.WaitingForWilda.value)
        val correctTransportListForWaitingForWilda = listOf(
                Transport(id = 5, applicationID = 5, destinationFrom = "Poznań", destinationTo = "Berlin",
                        departureDay = LocalDate.now().plusDays(10), departureHour = 10, departureMinute = 10,
                        vehicleSelect = 1, carrier = "RyanAir"),
                Transport(id = 6, applicationID = 5, destinationFrom = "Berlin", destinationTo = "Poznań",
                        departureDay = LocalDate.now().plusDays(15), departureHour = 12, departureMinute = 30,
                        vehicleSelect = 1, carrier = "RyanAir"))

        val correctApplicationForAccepted = Application(id = 6, firstName = "Jan", surname = "Kowalczyk",
                birthDate = LocalDate.of(2000, 1, 1), academicDegree = "Prof.",
                phoneNumber = "+48 123456789", employeeId = 170387, identityDocumentType = 1,
                identityDocumentNumber = "AB6789000", createdOn = LocalDate.of(2020, 12, 1), placeId = 5,
                abroadStartDate = LocalDate.now().plusDays(10), abroadEndDate = LocalDate.now().plusDays(15),
                instituteId = 1, purpose = "Konferencja", conference = "AntyCovid2020", subject = " TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą",
                conferenceStartDate = LocalDate.now().plusDays(11), conferenceEndDate = LocalDate.now().plusDays(14),
                financialSourceId = 1, abroadStartDateInsurance = LocalDate.now().plusDays(10),
                abroadEndDateInsurance = LocalDate.now().plusDays(15), selfInsured = false,
                advanceApplicationId = 3, prepaymentId = 3, comments = "User Comments",
                wildaComments = null, directorComments = "Director comments", rectorComments = null, status = StatusEnum.Accepted.value)
        val correctTransportListForAccepted = listOf(
                Transport(id = 5, applicationID = 6, destinationFrom = "Poznań", destinationTo = "Berlin",
                        departureDay = LocalDate.now().plusDays(10), departureHour = 10, departureMinute = 10,
                        vehicleSelect = 1, carrier = "RyanAir"),
                Transport(id = 6, applicationID = 6, destinationFrom = "Berlin", destinationTo = "Poznań",
                        departureDay = LocalDate.now().plusDays(15), departureHour = 12, departureMinute = 30,
                        vehicleSelect = 1, carrier = "RyanAir"))


        val correctApplicationForRector = Application(id = 4, firstName = "Jan", surname = "Kowalczyk",
                birthDate = LocalDate.of(2000, 1, 1), academicDegree = "Prof.",
                phoneNumber = "+48 123456789", employeeId = 170387, identityDocumentType = 2,
                identityDocumentNumber = "AB6789000", createdOn = LocalDate.of(2020, 12, 1), placeId = 5,
                abroadStartDate = LocalDate.now().plusDays(10), abroadEndDate = LocalDate.now().plusDays(15),
                instituteId = 1, purpose = "Konferencja", conference = "AntyCovid2020", subject = " TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą",
                conferenceStartDate = LocalDate.now().plusDays(11), conferenceEndDate = LocalDate.now().plusDays(14),
                financialSourceId = 1, abroadStartDateInsurance = LocalDate.now().plusDays(10),
                abroadEndDateInsurance = LocalDate.now().plusDays(14), selfInsured = false,
                advanceApplicationId = 3, prepaymentId = 3, comments = "User Comments",
                wildaComments = null, directorComments = "Director comments", rectorComments = null, status = StatusEnum.WaitingForRector.value)
        val correctTransportListForRector = listOf(
                Transport(id = 5, applicationID = 4, destinationFrom = "Poznań", destinationTo = "Berlin",
                        departureDay = LocalDate.now().plusDays(10), departureHour = 10, departureMinute = 10,
                        vehicleSelect = 1, carrier = "RyanAir"),
                Transport(id = 6, applicationID = 4, destinationFrom = "Berlin", destinationTo = "Poznań",
                        departureDay = LocalDate.now().plusDays(15), departureHour = 12, departureMinute = 30,
                        vehicleSelect = 1, carrier = "RyanAir"))


        val correctApplicationForDirector = Application(id = 3, firstName = "Jan", surname = "Kowalczyk",
                birthDate = LocalDate.of(2000, 1, 1), academicDegree = "Prof.",
                phoneNumber = "+48 123456789", employeeId = 170387, identityDocumentType = 2,
                identityDocumentNumber = "AB6789000", createdOn = LocalDate.of(2020, 12, 1), placeId = 5,
                abroadStartDate = LocalDate.now().plusDays(10), abroadEndDate = LocalDate.now().plusDays(15),
                instituteId = 1, purpose = "Konferencja", conference = "AntyCovid2020", subject = " TRISS: Wirtualizacja funkcjonowania Sekcji Współpracy z Zagranicą",
                conferenceStartDate = LocalDate.now().plusDays(11), conferenceEndDate = LocalDate.now().plusDays(14),
                financialSourceId = null, abroadStartDateInsurance = LocalDate.now().plusDays(10),
                abroadEndDateInsurance = LocalDate.now().plusDays(15), selfInsured = false,
                advanceApplicationId = 3, prepaymentId = 3, comments = "comments",
                wildaComments = null, directorComments = null, rectorComments = null, status = StatusEnum.WaitingForDirector.value)
        val correctInstitute = Institute(id = 1, name = "Instytut Architektury i Planowania Przestrzennego", active = true)
        val correctTransportListForDirector = listOf(
                Transport(id = 5, applicationID = 3, destinationFrom = "Poznań", destinationTo = "Berlin",
                        departureDay = LocalDate.now().plusDays(10), departureHour = 10, departureMinute = 10,
                        vehicleSelect = 1, carrier = "RyanAir"),
                Transport(id = 6, applicationID = 3, destinationFrom = "Berlin", destinationTo = "Poznań",
                        departureDay = LocalDate.now().plusDays(15), departureHour = 12, departureMinute = 30,
                        vehicleSelect = 1, carrier = "RyanAir"))
        val correctAdvanceApplicationForDirector = AdvanceApplication(id = 3, startDate = LocalDate.now().plusDays(10),
                endDate = LocalDate.now().plusDays(15), residenceDietAmount = BigDecimal(10).setScale(2),
                residenceDietQuantity = 10, residenceDietSum = BigDecimal(100).setScale(2), accommodationQuantity = 10,
                accommodationLimit = BigDecimal(10).setScale(2), accommodationSum = BigDecimal(100).setScale(2),
                otherCostsAmount = BigDecimal(100.00).setScale(2), otherCostsDescription = "OtherCosts", placeId = 5,
                travelCosts = BigDecimal(100).setScale(2), travelDietAmount = BigDecimal(100).setScale(2),
                advanceSum = BigDecimal(100 + 100 + 100 + 100).setScale(2))
        val correctAdvancePaymentsInfoForDirector = AdvancePaymentsInfo(conferenceFeeValue = BigDecimal.valueOf(100).setScale(2),
                conferenceFeePaymentTypeSelect = 2, accommodationFeeValue = BigDecimal.valueOf(100).setScale(2),
                accommodationFeePaymentTypeSelect = 3)
        val correctPlaceForDirector = Place(id = 5, city = "Berlin", country = "Niemcy")
        val exampleApplicationInfoForDirector = ApplicationInfo(
                application = correctApplicationForDirector,
                institute = correctInstitute,
                financialSource = null,
                transport = correctTransportListForDirector,
                advanceApplication = correctAdvanceApplicationForDirector,
                advancePayments = correctAdvancePaymentsInfoForDirector,
                place = correctPlaceForDirector)
        val exampleApplicationInfoForRector = ApplicationInfo(
                application = correctApplicationForRector,
                institute = correctInstitute,
                financialSource = correctFinancialSourceRectorAndWilda,
                transport = correctTransportListForRector,
                advanceApplication = correctAdvanceApplicationForDirector,
                advancePayments = correctAdvancePaymentsInfoForDirector,
                place = correctPlaceForDirector)
        val exampleApplicationInfoForWaitingForWilda = ApplicationInfo(
                application = correctApplicationForWaitingForWilda,
                institute = correctInstitute,
                financialSource = correctFinancialSourceRectorAndWilda,
                transport = correctTransportListForWaitingForWilda,
                advanceApplication = correctAdvanceApplicationForDirector,
                advancePayments = correctAdvancePaymentsInfoForDirector,
                place = correctPlaceForDirector)
        val exampleApplicationInfoForAccepted = ApplicationInfo(
                application = correctApplicationForAccepted,
                institute = correctInstitute,
                financialSource = correctFinancialSourceRectorAndWilda,
                transport = correctTransportListForAccepted,
                advanceApplication = correctAdvanceApplicationForDirector,
                advancePayments = correctAdvancePaymentsInfoForDirector,
                place = correctPlaceForDirector)


        val correctApplication = Application(id = null, firstName = "Jan", surname = "Kowalczyk", birthDate = LocalDate.of(2000, 1, 1)
                , academicDegree = "Prof.", phoneNumber = "+48 123456789", employeeId = 170387, identityDocumentType = 2,
                identityDocumentNumber = "identityDocumentNumber", createdOn = null, placeId = null,
                abroadStartDate = LocalDate.now().plusDays(10), abroadEndDate = LocalDate.now().plusDays(15),
                instituteId = 1, purpose = "purpose", conference = "conference", subject = "subject", conferenceStartDate = LocalDate.now().plusDays(11),
                conferenceEndDate = LocalDate.now().plusDays(14), financialSourceId = null, abroadStartDateInsurance = LocalDate.now().plusDays(10)
                , abroadEndDateInsurance = LocalDate.now().plusDays(15), selfInsured = false, advanceApplicationId = null, prepaymentId = null, comments = "blablabla",
                wildaComments = null, directorComments = null, rectorComments = null, status = StatusEnum.WaitingForDirector.value)
        val correctTransportList = listOf(Transport(id = null, applicationID = null, destinationFrom = "Poznań", destinationTo = "Berlin", departureDay = LocalDate.now().plusDays(10), departureHour = 10, departureMinute = 10, vehicleSelect = 3, carrier = "carrier"),
                Transport(id = null, applicationID = null, destinationFrom = "Berlin", destinationTo = "Poznań", departureDay = LocalDate.now().plusDays(15), departureHour = 10, departureMinute = 10, vehicleSelect = 3, carrier = "carrier"))
        val correctAdvanceApplication = AdvanceApplication(id = null, startDate = LocalDate.now().plusDays(10), endDate = LocalDate.now().plusDays(15),
                residenceDietAmount = BigDecimal(10).setScale(2), residenceDietQuantity = 10, residenceDietSum = BigDecimal(100).setScale(2),
                accommodationQuantity = 10, accommodationLimit = BigDecimal(10).setScale(2), accommodationSum = BigDecimal(100).setScale(2),
                otherCostsAmount = BigDecimal(100.00).setScale(2), otherCostsDescription = "blablabla", placeId = null, travelCosts = BigDecimal(100).setScale(2),
                travelDietAmount = BigDecimal(100).setScale(2), advanceSum = BigDecimal(100 + 100 + 100 + 100).setScale(2))
        val correctAdvancePaymentsInfo = AdvancePaymentsInfo(conferenceFeeValue = BigDecimal.valueOf(100).setScale(2), conferenceFeePaymentTypeSelect = 2,
                accommodationFeeValue = BigDecimal.valueOf(100).setScale(2), accommodationFeePaymentTypeSelect = 3)
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
                employeeId = null, instituteName = null, instituteId = null, country = null, abroadEndDate = null, abroadStartDate = null, city = null, status = null,statusEng = null,statusPl = null)
        val pageInfo = PageInfo(
            filter = filter,
                desc = false, orderBy = "id", pageSize = 100, pageNumber = 0)
        fun getExampleForUserApplication(user: Employee) = correctApplication.copy(firstName = user.firstName, surname = user.surname, birthDate = user.birthDate!!, academicDegree = user.academicDegree!!, phoneNumber = user.phoneNumber!!, employeeId = user.employeeId, instituteId = user.instituteID)
    }

}
