package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.Status
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ValidationService {

    fun validateApproveInstitute(institute: Institute){
        if (institute.id == null)
            throw InvalidRequestBodyException("InstituteId cannot be null")
    }

    fun validateApprovePlace(place: Place){
        if (place.id == null)
            throw InvalidRequestBodyException("PlaceId cannot be null")
    }

    fun validateApproveAdvanceApplication(advanceApplication: AdvanceApplication){
        if (advanceApplication.id == null)
            throw InvalidRequestBodyException("AdvanceApplicationId cannot be null")
        if (advanceApplication.placeId == null)
            throw InvalidRequestBodyException("AdvanceApplicationPlaceId cannot be null")

    }

    fun validateApproveFinancialSource(financialSource: FinancialSource?) {
        if (financialSource == null)
            throw InvalidRequestBodyException("FinancialSource cannot be null")
        if (financialSource.mpk == null)
            throw InvalidRequestBodyException("FinancialSourceMPK cannot be empty")
        if (financialSource.allocationAccount == null)
            throw InvalidRequestBodyException("FinancialSourceAllocationAccount cannot be empty")
        if (financialSource.financialSource == null)
            throw InvalidRequestBodyException("FinancialSourceSource cannot be empty")
        if (financialSource.project == null)
            throw InvalidRequestBodyException("FinancialSourceProject cannot be empty")
    }

    fun validateUserApplication(applicationInfo: ApplicationInfo, user: Employee) {
        checkStartDateBeforeEndDate(applicationInfo.application.abroadStartDate,
                applicationInfo.application.abroadEndDate, "AbroadStartDate is after abroadEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.application.conferenceStartDate,
                applicationInfo.application.conferenceEndDate, "ConferenceStartDate is after conferenceEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.advanceApplication.startDate,
                applicationInfo.advanceApplication.endDate, "RequestPaymentStartDate is after RequestPaymentEndDate.")
        checkStartDateBeforeEndDate(applicationInfo.application.abroadStartDateInsurance,
                applicationInfo.application.abroadEndDateInsurance, "AbroadStartDateInsurance is after abroadEndDateInsurance")
        if (applicationInfo.application.firstName != user.firstName)
            throw(InvalidRequestBodyException("FirstName is not equal first name in database"))
        if (applicationInfo.application.surname != user.surname)
            throw(InvalidRequestBodyException("Surname is not equal surname in database"))
        if (applicationInfo.application.directorComments != null)
            throw(InvalidRequestBodyException("Director comments must be null"))
        if (applicationInfo.application.rectorComments != null)
            throw(InvalidRequestBodyException("Rector comments must be null"))
        if (applicationInfo.application.wildaComments != null)
            throw(InvalidRequestBodyException("Wilda comments must be null"))
        if (applicationInfo.financialSource != null)
            throw(InvalidRequestBodyException("FinancialSource must be null"))
        if (applicationInfo.application.id != null)
            throw(InvalidRequestBodyException("Application Id must be null"))
        if (applicationInfo.application.status != Status.WaitingForDirector)
            throw(InvalidRequestBodyException("Application Status must be WaitingForDirector"))
        //TODO więcej walidacji do zrobienia
    }

    fun validateApproveApplicationInfo(applicationInfo: ApplicationInfo, role: Role) {
        validateApproveInstitute(applicationInfo.institute)
        validateApprovePlace(applicationInfo.place)
        validateApproveAdvanceApplication(applicationInfo.advanceApplication)
        validateApproveApplication(applicationInfo.application, role)
        validateApproveFinancialSource(applicationInfo.financialSource)
    }

    private fun validateApproveApplication(application: Application, role: Role) {
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


    private fun checkStartDateBeforeEndDate(startDate: LocalDate, endDate: LocalDate, message: String) {
        if (startDate.isAfter(endDate)) {
            throw(WrongDateException(message))
        }
    }
}
