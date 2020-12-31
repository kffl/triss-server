package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.RequestDataDiffersFromDatabaseDataException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.StatusEnum
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ValidationService {

    fun validateApproveApplicationInfo(applicationInfo: ApplicationInfo, role: Role) {
        validateStatus(applicationInfo.application, role)
        validateInstitute(applicationInfo.institute)
        validatePlace(applicationInfo.place)
        validateAdvanceApplication(applicationInfo.advanceApplication)
        validateApplication(applicationInfo.application, role)
        validateApproveFinancialSource(applicationInfo.financialSource, role)
    }

    fun validateRejectApplicationInfo(applicationInfo: ApplicationInfo, role: Role) {
        validateStatus(applicationInfo.application, role)
        validateInstitute(applicationInfo.institute)
        validatePlace(applicationInfo.place)
        validateAdvanceApplication(applicationInfo.advanceApplication)
        validateApplication(applicationInfo.application, role)
        if (role != Role.DIRECTOR)
            validateApproveFinancialSource(applicationInfo.financialSource, role)
    }

    fun validateCreateApplicationInfo(applicationInfo: ApplicationInfo, user: Employee) {
        validateEmployee(user)
        if (applicationInfo.financialSource != null)
            throw(InvalidRequestBodyException("FinancialSource must be null"))
        validateCreateApplication(applicationInfo.application, user)
        validateCreatePlace(applicationInfo.place)
        validateCreateAdvanceApplication(applicationInfo.advanceApplication)
        validateInstitute(applicationInfo.institute)
        validateDates(applicationInfo)
    }

    fun validateDates(applicationInfo: ApplicationInfo) {
        val application = applicationInfo.application
        if (!LocalDate.now().isBefore(application.abroadStartDate))
            throw(WrongDateException("abroadEndDate cannot be in past"))
        if (!LocalDate.now().isBefore(application.abroadEndDate))
            throw(WrongDateException("abroadEndDate cannot be in past"))
        if (application.abroadStartDate.isAfter(application.abroadEndDate)) {
            throw(WrongDateException("abroadStartDate is after abroadEndDate"))
        }
        if (!application.selfInsured) {
            if (application.abroadStartDateInsurance != null && application.abroadEndDateInsurance != null) {
                checkDateWithAbroadDates(
                    application.abroadStartDateInsurance, application.abroadEndDateInsurance,
                    application, "abroadInsurance"
                )
                if (!LocalDate.now().plusDays(4).isBefore(application.abroadStartDate))
                    throw(WrongDateException("insurance can be used only when application is crated 5 days or more before start date"))
            } else {
                throw InvalidRequestBodyException("abroadStartDate or abroadEndDate is missing")
            }
        }
        checkDateWithAbroadDates(
            application.conferenceStartDate, application.conferenceEndDate,
            application, "conference"
        )
        applicationInfo.transport.forEach { x ->
            checkDateWithAbroadDates(
                x.departureDay,
                x.departureDay,
                application,
                "departureDay"
            )
        }
        checkDateWithAbroadDates(
            applicationInfo.advanceApplication.startDate,
            applicationInfo.advanceApplication.endDate,
            application,
            "advanceApplication"
        )
    }

    private fun validateEmployee(user: Employee) {
        if (user.employeeType == null)
            throw InvalidRequestBodyException("This user has no Role")
        if (user.instituteID == null)
            throw InvalidRequestBodyException("This user has no Institute")
        if (user.academicDegree == null)
            throw InvalidRequestBodyException("This user has no Academic Degree")
        if (user.birthDate == null)
            throw InvalidRequestBodyException("This user has no birthDate")
        if (user.phoneNumber == null)
            throw InvalidRequestBodyException("This user has no phone number")
    }

    private fun validateInstitute(institute: Institute) {
        if (institute.name.isBlank())
            throw InvalidRequestBodyException("Institute name cannot be Blank")
    }

    private fun validateCreatePlace(place: Place) {
        if (place.id != null)
            throw InvalidRequestBodyException("PlaceId cannot be null")
        if (place.city.isBlank())
            throw InvalidRequestBodyException("City cannot be null")
        if (place.country.isBlank())
            throw InvalidRequestBodyException("Country cannot be null")
    }

    private fun validatePlace(place: Place) {
        if (place.id == null)
            throw InvalidRequestBodyException("PlaceId cannot be null")
        if (place.city.isBlank())
            throw InvalidRequestBodyException("City cannot be null")
        if (place.country.isBlank())
            throw InvalidRequestBodyException("Country cannot be null")
    }


    private fun validateAdvanceApplication(advanceApplication: AdvanceApplication) {
        if (advanceApplication.id == null)
            throw InvalidRequestBodyException("AdvanceApplicationId cannot be null")
        if (advanceApplication.placeId == null)
            throw InvalidRequestBodyException("AdvanceApplicationPlaceId cannot be null")
    }

    private fun validateCreateAdvanceApplication(advanceApplication: AdvanceApplication) {
        if (advanceApplication.id != null)
            throw InvalidRequestBodyException("AdvanceApplicationId must be null")
        if (advanceApplication.placeId != null)
            throw InvalidRequestBodyException("AdvanceApplicationPlaceId must be null")
    }


    private fun validateApproveFinancialSource(financialSource: FinancialSource?, role: Role) {
        if (financialSource == null)
            throw InvalidRequestBodyException("FinancialSource cannot be null")
        if (financialSource.mpk.isNullOrBlank())
            throw InvalidRequestBodyException("FinancialSourceMPK cannot be empty")
        if (financialSource.allocationAccount.isNullOrBlank())
            throw InvalidRequestBodyException("FinancialSourceAllocationAccount cannot be empty")
        if (financialSource.financialSource.isNullOrBlank())
            throw InvalidRequestBodyException("FinancialSourceSource cannot be empty")
        if (financialSource.project.isNullOrBlank())
            throw InvalidRequestBodyException("FinancialSourceProject cannot be empty")
    }


    private fun validateCreateApplication(application: Application, user: Employee) {
        if (application.id != null)
            throw(InvalidRequestBodyException("Application Id must be null"))
        if (application.firstName != user.firstName)
            throw(RequestDataDiffersFromDatabaseDataException("FirstName"))
        if (application.surname != user.surname)
            throw(RequestDataDiffersFromDatabaseDataException("Surname"))
        if (application.academicDegree != user.academicDegree)
            throw(RequestDataDiffersFromDatabaseDataException("AcademicDegree"))
        if (application.phoneNumber != user.phoneNumber)
            throw(RequestDataDiffersFromDatabaseDataException("Phone number"))
        if (application.employeeId != user.employeeId)
            throw(RequestDataDiffersFromDatabaseDataException("EmployeeId"))
        if (application.identityDocumentNumber.isBlank())
            throw(InvalidRequestBodyException("identityDocumentNumber in Application is  blank"))
        if (application.createdOn != null)
            throw(InvalidRequestBodyException("createdOn in Application must be null"))
        if (application.placeId != null)
            throw(InvalidRequestBodyException("PlaceId must be null"))
        if (application.purpose.isBlank())
            throw(InvalidRequestBodyException("Purpose cannot be blank"))
        if (application.conference.isBlank())
            throw(InvalidRequestBodyException("Conference cannot be blank"))
        if (application.subject.isBlank())
            throw(InvalidRequestBodyException("Subject cannot be blank"))
        if (application.financialSourceId != null)
            throw(InvalidRequestBodyException("FinancialSourceId must be null"))
        if (application.selfInsured) {
            if (application.abroadStartDateInsurance != null)
                throw(InvalidRequestBodyException("AbroadStartDateInsurance must be null"))
            if (application.abroadEndDateInsurance != null)
                throw(InvalidRequestBodyException("AbroadEndDateInsurance must be null"))
        } else {
            if (application.abroadStartDateInsurance == null)
                throw(InvalidRequestBodyException("AbroadStartDateInsurance cannot be null"))
            if (application.abroadEndDateInsurance == null)
                throw(InvalidRequestBodyException("AbroadEndDateInsurance cannot be null"))
        }
        if (application.advanceApplicationId != null)
            throw(InvalidRequestBodyException("AdvanceApplicationId must be null"))
        if (application.prepaymentId != null)
            throw(InvalidRequestBodyException("PrepaymentId must be null"))
        if (application.directorComments != null)
            throw(InvalidRequestBodyException("Director comments must be null"))
        if (application.rectorComments != null)
            throw(InvalidRequestBodyException("Rector comments must be null"))
        if (application.wildaComments != null)
            throw(InvalidRequestBodyException("Wilda comments must be null"))
        if (application.status != StatusEnum.WaitingForDirector.value)
            throw(InvalidRequestBodyException("Application Status must be WaitingForDirector"))

    }

    private fun validateApplication(application: Application, role: Role) {
        if (application.id == null)
            throw(InvalidRequestBodyException("Application Id cannot be null"))
        if (application.advanceApplicationId == null)
            throw(InvalidRequestBodyException("Advance Application Id cannot be null"))
        if (application.createdOn == null)
            throw(InvalidRequestBodyException("Created On cannot be null"))
        if (application.employeeId == null)
            throw(InvalidRequestBodyException("EmployeeId cannot be null"))
        if (application.instituteId == null)
            throw(InvalidRequestBodyException("InstituteId cannot be null"))
        if (application.placeId == null)
            throw(InvalidRequestBodyException("PlaceId cannot be null"))
        if (application.prepaymentId == null)
            throw(InvalidRequestBodyException("PrepaymentId cannot be null"))
        when (role) {
            Role.DIRECTOR, Role.USER ->
                if (application.financialSourceId != null)
                    throw(InvalidRequestBodyException("FinancialSourceId must be null"))
            else ->
                if (application.financialSourceId == null)
                    throw(InvalidRequestBodyException("FinancialSourceId cannot null"))
        }

    }

    private fun validateStatus(application: Application, role: Role) {
        when (role) {
            Role.DIRECTOR -> {
                if (application.status != StatusEnum.WaitingForDirector.value)
                    throw(InvalidRequestBodyException("Status must be WaitingForDirector"))
            }
            Role.WILDA -> {
                if (application.status != StatusEnum.WaitingForWilda.value)
                    throw(InvalidRequestBodyException("Status must be WaitingForWilda"))
            }
            Role.RECTOR -> {
                if (application.status != StatusEnum.WaitingForRector.value)
                    throw(InvalidRequestBodyException("Status must be WaitingForRector"))
            }
            Role.USER -> {
                throw InvalidRequestBodyException("User don't have access to this")
            }
        }
    }

    private fun checkDateWithAbroadDates(
        startDate: LocalDate,
        endDate: LocalDate,
        application: Application,
        dateName: String
    ) {
        if (startDate.isAfter(endDate)) {
            throw(WrongDateException("${dateName}StartDate is after ${dateName}EndDate"))
        }
        if (endDate.isAfter(application.abroadEndDate)) {
            throw(WrongDateException("${dateName}EndDate is after abroadEndDate"))
        }
        if (startDate.isBefore(application.abroadStartDate)) {
            throw(WrongDateException("${dateName}Date is before abroadStartDate"))
        }
    }

    fun validateCreateSettlementApplication(application: Application, user: Employee){
        if (!application.abroadEndDate.isBefore(LocalDate.now()))
            throw(WrongDateException("This trip has not been completed."))
        if (application.status != StatusEnum.Accepted.value)
            throw(InvalidRequestBodyException("Status must be Accepted"))
        if (application.employeeId != user.employeeId)
            throw UnauthorizedException("This is not a user {${user.employeeId},${user.firstName},${user.surname}} application")
    }
}

