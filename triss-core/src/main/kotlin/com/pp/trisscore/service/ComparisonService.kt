package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.Status
import org.springframework.stereotype.Service

@Service
class ComparisonService {
    fun compareInstitutes(dbInstitute: Institute, reqInstitute: Institute) {
        if(dbInstitute.id != reqInstitute.id)
            throw InvalidRequestBodyException("InstituteID in DB differs from request's InstituteID ")
        if(dbInstitute.name != reqInstitute.name)
            throw InvalidRequestBodyException("InstituteName in DB differs from request's InstituteName")
        if(dbInstitute.active != reqInstitute.active)
            throw InvalidRequestBodyException("InstituteActive in DB differs from request's InstituteActive")
    }

    fun comparePlaces(dbPlace: Place, reqPlace: Place){
        if(dbPlace.id != reqPlace.id)
            throw InvalidRequestBodyException("")
        if(dbPlace.country != reqPlace.country)
            throw InvalidRequestBodyException("")
        if(dbPlace.city != reqPlace.city)
            throw InvalidRequestBodyException("")
    }

    fun compareApplications(dbApplication: Application, reqApplication: Application, role: Role) {
        if (dbApplication.id != reqApplication.id)
            throw InvalidRequestBodyException("")
        if (dbApplication.firstName != reqApplication.firstName)
            throw InvalidRequestBodyException("")
        if (dbApplication.surname != reqApplication.surname)
            throw InvalidRequestBodyException("")
        if (dbApplication.birthDate != reqApplication.birthDate)
            throw InvalidRequestBodyException("")
        if (dbApplication.academicDegree != reqApplication.academicDegree)
            throw InvalidRequestBodyException("")
        if (dbApplication.phoneNumber != reqApplication.phoneNumber)
            throw InvalidRequestBodyException("")
        if (dbApplication.employeeId != reqApplication.employeeId)
            throw InvalidRequestBodyException("")
        if (dbApplication.identityDocumentType != reqApplication.identityDocumentType)
            throw InvalidRequestBodyException("")
        if (dbApplication.identityDocumentNumber != reqApplication.identityDocumentNumber)
            throw InvalidRequestBodyException("")
        if (dbApplication.createdOn != reqApplication.createdOn)
            throw InvalidRequestBodyException("")
        if (dbApplication.placeId != reqApplication.placeId)
            throw InvalidRequestBodyException("")
        if (dbApplication.abroadStartDate != reqApplication.abroadStartDate)
            throw InvalidRequestBodyException("")
        if (dbApplication.abroadEndDate != reqApplication.abroadEndDate)
            throw InvalidRequestBodyException("")
        if (dbApplication.instituteId != reqApplication.instituteId)
            throw InvalidRequestBodyException("")
        if (dbApplication.purpose != reqApplication.purpose)
            throw InvalidRequestBodyException("")
        if (dbApplication.conference != reqApplication.conference)
            throw InvalidRequestBodyException("")
        if (dbApplication.subject != reqApplication.subject)
            throw InvalidRequestBodyException("")
        if (dbApplication.conferenceStartDate != reqApplication.conferenceStartDate)
            throw InvalidRequestBodyException("")
        if (dbApplication.conferenceEndDate != reqApplication.conferenceEndDate)
            throw InvalidRequestBodyException("")
        if (dbApplication.abroadStartDateInsurance != reqApplication.abroadStartDateInsurance)
            throw InvalidRequestBodyException("")
        if (dbApplication.abroadEndDateInsurance != reqApplication.abroadEndDateInsurance)
            throw InvalidRequestBodyException("")
        if (dbApplication.selfInsured != reqApplication.selfInsured)
            throw InvalidRequestBodyException("")
        if (dbApplication.advanceApplicationId != reqApplication.advanceApplicationId)
            throw InvalidRequestBodyException("")
        if (dbApplication.prepaymentId != reqApplication.prepaymentId)
            throw InvalidRequestBodyException("")
        if (dbApplication.comments != reqApplication.comments)
            throw InvalidRequestBodyException("")
        when (role) {
            Role.WILDA -> {
                if (dbApplication.financialSourceId != reqApplication.financialSourceId)
                    throw InvalidRequestBodyException("")
                if (dbApplication.directorComments != reqApplication.directorComments)
                    throw InvalidRequestBodyException("")
                if (dbApplication.rectorComments != reqApplication.rectorComments)
                    throw InvalidRequestBodyException("")
                if (dbApplication.status != Status.WaitingForWilda || dbApplication.status!= Status.WaitingForWildaAgain)
                    throw InvalidRequestBodyException("")
                return
            }
            Role.DIRECTOR -> {
                if (dbApplication.wildaComments != reqApplication.wildaComments)
                    throw InvalidRequestBodyException("")
                if (dbApplication.rectorComments != reqApplication.rectorComments)
                    throw InvalidRequestBodyException("")
                if (dbApplication.status != Status.WaitingForDirector)
                    throw InvalidRequestBodyException("")
                return
            }
            Role.RECTOR -> {
                if (dbApplication.financialSourceId != reqApplication.financialSourceId)
                    throw InvalidRequestBodyException("")
                if (dbApplication.directorComments != reqApplication.directorComments)
                    throw InvalidRequestBodyException("")
                if (dbApplication.wildaComments != reqApplication.wildaComments)
                    throw InvalidRequestBodyException("")
                if (dbApplication.status != Status.WaitingForDirector)
                    throw InvalidRequestBodyException("")
                return
            }
            Role.USER -> {throw UnauthorizedException("")}
        }
    }

    fun compareFinancialSources(dbFinancialSource: FinancialSource, reqFinancialSource: FinancialSource){
        if(dbFinancialSource.id != reqFinancialSource.id)
            throw InvalidRequestBodyException("")
        if(dbFinancialSource.allocationAccount != reqFinancialSource.allocationAccount)
            throw InvalidRequestBodyException("")
        if(dbFinancialSource.MPK != reqFinancialSource.MPK)
            throw InvalidRequestBodyException("")
        if(dbFinancialSource.financialSource != reqFinancialSource.financialSource)
            throw InvalidRequestBodyException("")
        if(dbFinancialSource.project != reqFinancialSource.project)
            throw InvalidRequestBodyException("")
    }

    fun compareTransports(dbTransport: Transport, reqTransport: Transport){
        if(dbTransport.id != reqTransport.id)
            throw InvalidRequestBodyException("")
        if(dbTransport.applicationID != reqTransport.applicationID)
            throw InvalidRequestBodyException("")
        if(dbTransport.destinationFrom != reqTransport.destinationFrom)
            throw InvalidRequestBodyException("")
        if(dbTransport.destinationTo != reqTransport.destinationTo)
            throw InvalidRequestBodyException("")
        if(dbTransport.departureDay != reqTransport.departureDay)
            throw InvalidRequestBodyException("")
        if(dbTransport.departureMinute != reqTransport.departureMinute)
            throw InvalidRequestBodyException("")
        if(dbTransport.departureHour != reqTransport.departureHour)
            throw InvalidRequestBodyException("")
        if(dbTransport.vehicleSelect != reqTransport.vehicleSelect)
            throw InvalidRequestBodyException("")
        if(dbTransport.carrier != reqTransport.carrier)
            throw InvalidRequestBodyException("")
    }

    fun compareAdvanceApplication(dbAdvanceApplication: AdvanceApplication, reqAdvanceApplication: AdvanceApplication){
        if(dbAdvanceApplication.id != reqAdvanceApplication.id)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.placeId != reqAdvanceApplication.placeId)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.startDate != reqAdvanceApplication.startDate)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.endDate != reqAdvanceApplication.endDate)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.residenceDietQuantity != reqAdvanceApplication.residenceDietQuantity)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.residenceDietAmount != reqAdvanceApplication.residenceDietAmount)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.accommodationQuantity != reqAdvanceApplication.accommodationQuantity)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.accommodationLimit != reqAdvanceApplication.accommodationLimit)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.travelDietAmount != reqAdvanceApplication.travelDietAmount)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.travelCosts != reqAdvanceApplication.travelCosts)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.otherCostsDescription != reqAdvanceApplication.otherCostsDescription)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.otherCostsAmount != reqAdvanceApplication.otherCostsAmount)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.residenceDietSum != reqAdvanceApplication.residenceDietSum)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.accommodationSum != reqAdvanceApplication.accommodationSum)
            throw InvalidRequestBodyException("")
        if(dbAdvanceApplication.advanceSum != reqAdvanceApplication.advanceSum)
            throw InvalidRequestBodyException("")
    }

    fun compareAdvancePayments(dbAdvancePaymentsInfo: AdvancePaymentsInfo, reqAdvancePaymentsInfo: AdvancePaymentsInfo){
        if(dbAdvancePaymentsInfo.conferenceFeeValue != reqAdvancePaymentsInfo.conferenceFeeValue)
            throw InvalidRequestBodyException("")
        if(dbAdvancePaymentsInfo.conferenceFeePaymentTypeSelect != reqAdvancePaymentsInfo.conferenceFeePaymentTypeSelect)
            throw InvalidRequestBodyException("")
        if(dbAdvancePaymentsInfo.accommodationFeeValue != reqAdvancePaymentsInfo.accommodationFeeValue)
            throw InvalidRequestBodyException("")
        if(dbAdvancePaymentsInfo.accommodationFeePaymentTypeSelect != reqAdvancePaymentsInfo.accommodationFeePaymentTypeSelect)
            throw InvalidRequestBodyException("")
    }
}