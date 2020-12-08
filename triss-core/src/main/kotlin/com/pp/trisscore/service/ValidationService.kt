package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.Status
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ValidationService {

    fun validateApproveApplicationInfo(applicationInfo: ApplicationInfo, role: Role) {
        validateApproveStatus(applicationInfo.application, role)
        validateInstitute(applicationInfo.institute)
        validatePlace(applicationInfo.place)
        validateAdvanceApplication(applicationInfo.advanceApplication)
        validateApplication(applicationInfo.application, role)
        validateApproveFinancialSource(applicationInfo.financialSource, role)
    }

    fun validateRejectApplicationInfo(applicationInfo: ApplicationInfo, role: Role) {
        validateRejectStatus(applicationInfo.application, role)
        validateInstitute(applicationInfo.institute)
        validatePlace(applicationInfo.place)
        validateAdvanceApplication(applicationInfo.advanceApplication)
        validateApplication(applicationInfo.application, role)
        if (role != Role.DIRECTOR)
            validateApproveFinancialSource(applicationInfo.financialSource, role)
    }

    fun validateCreateApplicationInfo(applicationInfo: ApplicationInfo, user: Employee) {
        if (user.employeeType == null)
            throw InvalidRequestBodyException("This user has no Role")
        if (applicationInfo.financialSource != null)
            throw(InvalidRequestBodyException("FinancialSource must be null"))
        validateCreateApplication(applicationInfo.application, user)
        validateCreatePlace(applicationInfo.place)
        validateCreateAdvanceApplication(applicationInfo.advanceApplication)
        validateInstitute(applicationInfo.institute)
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
            throw InvalidRequestBodyException("City cannot be null")
    }

    private fun validatePlace(place: Place) {
        if (place.id == null)
            throw InvalidRequestBodyException("PlaceId cannot be null")
        if (place.city.isBlank())
            throw InvalidRequestBodyException("City cannot be null")
        if (place.country.isBlank())
            throw InvalidRequestBodyException("City cannot be null")
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
            throw(InvalidRequestBodyException("FirstName is not equal first name in database"))
        if (application.surname != user.surname)
            throw(InvalidRequestBodyException("Surname in Application differ from the User one"))
        if (application.academicDegree != user.academicDegree)
            throw(InvalidRequestBodyException("AcademicDegree in Application differ from the User one"))
        if (application.phoneNumber != user.phoneNumber)
            throw(InvalidRequestBodyException("Phone number in Application differ from the User one"))
        if (application.employeeId != user.employeeId)
            throw(InvalidRequestBodyException("EmployeeId in Application differ from the User one"))
        if (application.identityDocumentNumber.isBlank())
            throw(InvalidRequestBodyException("identityDocumentNumber in Application is  blank"))
        if (application.createdOn != null)
            throw(InvalidRequestBodyException("createdOn in Application must be null"))
        if (application.placeId != null)
            throw(InvalidRequestBodyException("PlaceId must be null"))
        checkStartDateBeforeEndDate(application.abroadStartDate,
                application.abroadEndDate, "AbroadStartDate is after abroadEndDate.")
        if (application.purpose.isBlank())
            throw(InvalidRequestBodyException("Purpose cannot be blank"))
        if (application.conference.isBlank())
            throw(InvalidRequestBodyException("Conference cannot be blank"))
        if (application.subject.isBlank())
            throw(InvalidRequestBodyException("Subject cannot be blank"))
        checkStartDateBeforeEndDate(application.conferenceStartDate,
                application.conferenceEndDate, "ConferenceStartDate is after conferenceEndDate.")
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
        if (application.status != Status.WaitingForDirector)
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


    private fun validateApproveStatus(application: Application, role: Role) {
        when (role) {
            Role.DIRECTOR -> {
                if (application.status != Status.WaitingForWilda)
                    throw(UnauthorizedException("Status must be WaitingForWilda"))
            }
            Role.WILDA -> {
                if (application.status != Status.WaitingForRector)
                    throw(UnauthorizedException("Status must be WaitingForRector"))
            }
            Role.RECTOR -> {
                if (application.status != Status.Accepted)
                    throw(UnauthorizedException("Status must be Accepted"))
            }
            Role.USER -> {
                throw UnauthorizedException("User don't have access to this")
            }
        }
    }

    private fun validateRejectStatus(application: Application, role: Role) {
        when (role) {
            Role.DIRECTOR -> {
                if (application.status != Status.RejectedByDirector)
                    throw(UnauthorizedException("Status must be RejectedByDirector"))
            }
            Role.WILDA -> {
                if (application.status != Status.RejectedByWilda)
                    throw(UnauthorizedException("Status must be RejectedByWilda"))
            }
            Role.RECTOR -> {
                if (application.status != Status.RejectedByRector)
                    throw(UnauthorizedException("Status must be RejectedByRector"))
            }
            Role.USER -> {
                throw UnauthorizedException("User don't have access to this")
            }
        }
    }


    private fun checkStartDateBeforeEndDate(startDate: LocalDate, endDate: LocalDate, message: String) {
        if (startDate.isAfter(endDate)) {
            throw(WrongDateException(message))
        }
    }
}
