package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.Status
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ValidationService {

    fun validateFinancialSource(financialSource: FinancialSource?) {
        if (financialSource == null)
            throw InvalidRequestBodyException("FinancialSourceId cannot be null")
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

    private fun validateApproveApplication(applicationInfo: ApplicationInfo){
        if (applicationInfo.application == null)
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
    }

    private fun checkStartDateBeforeEndDate(startDate: LocalDate, endDate: LocalDate, message: String) {
        if (startDate.isAfter(endDate)) {
            throw(WrongDateException(message))
        }
    }
}
