package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.model.enums.Status
import org.springframework.stereotype.Service

@Service
class ComparisonService {

    fun compareApproveApplicationsInfo(dbApplicationInfo: ApplicationInfo, reqApplicationInfo: ApplicationInfo, role: Role) {
        compareApproveApplication(role, dbApplicationInfo.application, reqApplicationInfo.application)
        compareInstitutes(dbApplicationInfo.institute, reqApplicationInfo.institute)
        if (role != Role.DIRECTOR)
            compareFinancialSources(dbApplicationInfo.financialSource!!, reqApplicationInfo.financialSource!!)
        compareAdvanceApplication(dbApplicationInfo.advanceApplication, reqApplicationInfo.advanceApplication)
        compareAdvancePayments(dbApplicationInfo.advancePayments, reqApplicationInfo.advancePayments)
    }

    fun compareRejectedApplicationsInfo(dbApplicationInfo: ApplicationInfo, reqApplicationInfo: ApplicationInfo, role: Role) {
        compareRejectedApplications(role, dbApplicationInfo.application, reqApplicationInfo.application)
        compareInstitutes(dbApplicationInfo.institute, reqApplicationInfo.institute)
        if (role != Role.DIRECTOR) {
            compareFinancialSources(dbApplicationInfo.financialSource!!, reqApplicationInfo.financialSource!!)
        }
        compareAdvanceApplication(dbApplicationInfo.advanceApplication, reqApplicationInfo.advanceApplication)
        compareAdvancePayments(dbApplicationInfo.advancePayments, reqApplicationInfo.advancePayments)
    }

    fun compareInstitutes(dbInstitute: Institute, reqInstitute: Institute) {
        if (dbInstitute.id != reqInstitute.id)
            throw InvalidRequestBodyException("InstituteId in DB differs from the request's InstituteId ")
        if (dbInstitute.name != reqInstitute.name)
            throw InvalidRequestBodyException("InstituteName in DB differs from the request's InstituteName")
    }

    fun comparePlaces(dbPlace: Place, reqPlace: Place) {
        if (dbPlace.id != reqPlace.id)
            throw InvalidRequestBodyException("PlaceId in DB differs from the request's PlaceId")
        if (dbPlace.country != reqPlace.country)
            throw InvalidRequestBodyException("PlaceCountry in DB differs from the request's PlaceCountry")
        if (dbPlace.city != reqPlace.city)
            throw InvalidRequestBodyException("PlaceCity in DB differs from the request's PlaceCity")
    }

    private fun compareBaseApplications(dbApplication: Application, reqApplication: Application, role: Role) {
        if (dbApplication.id != reqApplication.id)
            throw InvalidRequestBodyException("ApplicationId in DB differs from the request's one")
        if (dbApplication.firstName != reqApplication.firstName)
            throw InvalidRequestBodyException("ApplicationFirstName in DB differs from the request's one")
        if (dbApplication.surname != reqApplication.surname)
            throw InvalidRequestBodyException("ApplicationSurname in DB differs from the request's one")
        if (dbApplication.birthDate != reqApplication.birthDate)
            throw InvalidRequestBodyException("ApplicationBirthDate in DB differs from the request's one")
        if (dbApplication.academicDegree != reqApplication.academicDegree)
            throw InvalidRequestBodyException("ApplicationAcademicDegree in DB differs from the request's one")
        if (dbApplication.phoneNumber != reqApplication.phoneNumber)
            throw InvalidRequestBodyException("ApplicationPhoneNumber in DB differs from the request's one")
        if (dbApplication.employeeId != reqApplication.employeeId)
            throw InvalidRequestBodyException("ApplicationEmployeeId in DB differs from the request's one")
        if (dbApplication.identityDocumentType != reqApplication.identityDocumentType)
            throw InvalidRequestBodyException("ApplicationIdentityDocumentType in DB differs from the request's one")
        if (dbApplication.identityDocumentNumber != reqApplication.identityDocumentNumber)
            throw InvalidRequestBodyException("ApplicationIdentityDocumentNumber in DB differs from the request's one")
        if (dbApplication.createdOn != reqApplication.createdOn)
            throw InvalidRequestBodyException("ApplicationCreatedOn in DB differs from the request's one")
        if (dbApplication.placeId != reqApplication.placeId)
            throw InvalidRequestBodyException("ApplicationPlaceId in DB differs from request's one")
        if (dbApplication.abroadStartDate != reqApplication.abroadStartDate)
            throw InvalidRequestBodyException("ApplicationAbroadStartDate in DB differs from the request's one")
        if (dbApplication.abroadEndDate != reqApplication.abroadEndDate)
            throw InvalidRequestBodyException("ApplicationAbroadEndDate in DB differs from the request's one")
        if (dbApplication.instituteId != reqApplication.instituteId)
            throw InvalidRequestBodyException("ApplicationInstituteId in DB differs from the request's one")
        if (dbApplication.purpose != reqApplication.purpose)
            throw InvalidRequestBodyException("ApplicationPurpose in DB differs from the request's one")
        if (dbApplication.conference != reqApplication.conference)
            throw InvalidRequestBodyException("ApplicationConference in DB differs from the request's one")
        if (dbApplication.subject != reqApplication.subject)
            throw InvalidRequestBodyException("ApplicationSubject in DB differs from the request's one")
        if (dbApplication.conferenceStartDate != reqApplication.conferenceStartDate)
            throw InvalidRequestBodyException("ApplicationConferenceStartDate in DB differs from the request's one")
        if (dbApplication.conferenceEndDate != reqApplication.conferenceEndDate)
            throw InvalidRequestBodyException("ApplicationConferenceEndDate in DB differs from the request's one")
        if (dbApplication.abroadStartDateInsurance != reqApplication.abroadStartDateInsurance)
            throw InvalidRequestBodyException("ApplicationAbroadStartDateInsurance in DB differs from the request's one")
        if (dbApplication.abroadEndDateInsurance != reqApplication.abroadEndDateInsurance)
            throw InvalidRequestBodyException("ApplicationAbroadEndDateInsurance in DB differs from the request's one")
        if (dbApplication.selfInsured != reqApplication.selfInsured)
            throw InvalidRequestBodyException("ApplicationSelfInsured in DB differs from the request's one")
        if (dbApplication.advanceApplicationId != reqApplication.advanceApplicationId)
            throw InvalidRequestBodyException("ApplicationAdvanceApplicationId in DB differs from the request's one")
        if (dbApplication.prepaymentId != reqApplication.prepaymentId)
            throw InvalidRequestBodyException("ApplicationPrepaymentId in DB differs from the request's one")
        if (dbApplication.comments != reqApplication.comments)
            throw InvalidRequestBodyException("ApplicationComments in DB differs from the request's one")
        when (role) {
            Role.WILDA -> {
                if (dbApplication.financialSourceId != reqApplication.financialSourceId)
                    throw InvalidRequestBodyException("ApplicationFinancialSourceId in DB differs from the request's one")
                if (dbApplication.directorComments != reqApplication.directorComments)
                    throw InvalidRequestBodyException("ApplicationDirectorComments in DB differs from the request's one")
                if (dbApplication.rectorComments != reqApplication.rectorComments)
                    throw InvalidRequestBodyException("ApplicationRectorComments in DB differs from the request's one")
                if (dbApplication.status != Status.WaitingForWilda)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.DIRECTOR -> {
                if (dbApplication.wildaComments != reqApplication.wildaComments)
                    throw InvalidRequestBodyException("ApplicationWildaComments in DB differs from the request's one")
                if (dbApplication.rectorComments != reqApplication.rectorComments)
                    throw InvalidRequestBodyException("ApplicationRectorComments in DB differs from the request's one")
                if (dbApplication.status != Status.WaitingForDirector)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.RECTOR -> {
                if (dbApplication.financialSourceId != reqApplication.financialSourceId)
                    throw InvalidRequestBodyException("ApplicationFinancialSourceId in DB differs from the request's one")
                if (dbApplication.directorComments != reqApplication.directorComments)
                    throw InvalidRequestBodyException("ApplicationDirectorComments in DB differs from the request's one")
                if (dbApplication.wildaComments != reqApplication.wildaComments)
                    throw InvalidRequestBodyException("ApplicationWildaComments in DB differs from the request's one")
                if (dbApplication.status != Status.WaitingForRector)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
            }
            Role.USER -> {
                throw UnauthorizedException("You do not have permission to perform this action")
            }
        }

    }

    private fun compareApproveApplication(role: Role, dbApplication: Application, reqApplication: Application) {
        compareBaseApplications(dbApplication, reqApplication, role)
        when (role) {
            Role.WILDA -> {
                if (reqApplication.status != Status.WaitingForRector)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.DIRECTOR -> {
                if (reqApplication.status != Status.WaitingForWilda)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.RECTOR -> {
                if (reqApplication.status != Status.Accepted)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.USER -> {
                throw UnauthorizedException("You do not have permission to perform this action")
            }
        }
    }

    private fun compareRejectedApplications(role: Role, dbApplication: Application, reqApplication: Application) {
        compareBaseApplications(dbApplication, reqApplication, role)
        when (role) {
            Role.WILDA -> {
                if (reqApplication.status != Status.RejectedByWilda)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.DIRECTOR -> {
                if (reqApplication.status != Status.RejectedByDirector)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.RECTOR -> {
                if (reqApplication.status != Status.RejectedByRector)
                    throw InvalidRequestBodyException("ApplicationStatus in DB differs from the request's one")
                return
            }
            Role.USER -> {
                throw UnauthorizedException("You do not have permission to perform this action")
            }
        }
    }

    fun compareFinancialSources(dbFinancialSource: FinancialSource, reqFinancialSource: FinancialSource) {
        if (dbFinancialSource.id != reqFinancialSource.id)
            throw InvalidRequestBodyException("FinancialSourceId in DB differs from the request's one")
        if (dbFinancialSource.allocationAccount != reqFinancialSource.allocationAccount)
            throw InvalidRequestBodyException("FinancialSourceAllocationAccount in DB differs from the request's one")
        if (dbFinancialSource.mpk != reqFinancialSource.mpk)
            throw InvalidRequestBodyException("FinancialSourceMPK in DB differs from the request's one")
        if (dbFinancialSource.financialSource != reqFinancialSource.financialSource)
            throw InvalidRequestBodyException("FinancialSource in DB differs from the request's one")
        if (dbFinancialSource.project != reqFinancialSource.project)
            throw InvalidRequestBodyException("FinancialSourceProject in DB differs from the request's one")
    }

    fun compareTransports(dbTransport: Transport, reqTransport: Transport) {
        if (dbTransport.id != reqTransport.id)
            throw InvalidRequestBodyException("TransportId in DB differs from the request's one")
        if (dbTransport.applicationID != reqTransport.applicationID)
            throw InvalidRequestBodyException("TransportApplicationID in DB differs from the request's one")
        if (dbTransport.destinationFrom != reqTransport.destinationFrom)
            throw InvalidRequestBodyException("TransportDestinationFrom in DB differs from the request's one")
        if (dbTransport.destinationTo != reqTransport.destinationTo)
            throw InvalidRequestBodyException("TransportDestinationTo in DB differs from the request's one")
        if (dbTransport.departureDay != reqTransport.departureDay)
            throw InvalidRequestBodyException("TransportDepartureDay in DB differs from the request's one")
        if (dbTransport.departureMinute != reqTransport.departureMinute)
            throw InvalidRequestBodyException("TransportDepartureMinute in DB differs from the request's one")
        if (dbTransport.departureHour != reqTransport.departureHour)
            throw InvalidRequestBodyException("TransportDepartureHour in DB differs from the request's one")
        if (dbTransport.vehicleSelect != reqTransport.vehicleSelect)
            throw InvalidRequestBodyException("TransportVehicleSelect in DB differs from the request's one")
        if (dbTransport.carrier != reqTransport.carrier)
            throw InvalidRequestBodyException("TransportCarrier in DB differs from the request's one")
    }

    fun compareAdvanceApplication(dbAdvanceApplication: AdvanceApplication, reqAdvanceApplication: AdvanceApplication) {
        if (dbAdvanceApplication.id != reqAdvanceApplication.id)
            throw InvalidRequestBodyException("AdvanceApplicationId in DB differs from the request's one")
        if (dbAdvanceApplication.placeId != reqAdvanceApplication.placeId)
            throw InvalidRequestBodyException("AdvanceApplicationPlaceId in DB differs from the request's one")
        if (dbAdvanceApplication.startDate != reqAdvanceApplication.startDate)
            throw InvalidRequestBodyException("AdvanceApplicationStartDate in DB differs from the request's one")
        if (dbAdvanceApplication.endDate != reqAdvanceApplication.endDate)
            throw InvalidRequestBodyException("AdvanceApplicationEndDate in DB differs from the request's one")
        if (dbAdvanceApplication.residenceDietQuantity != reqAdvanceApplication.residenceDietQuantity)
            throw InvalidRequestBodyException("AdvanceApplicationResidenceDietQuantity in DB differs from the request's one")
        if (dbAdvanceApplication.residenceDietAmount.toFloat() != reqAdvanceApplication.residenceDietAmount.toFloat())
            throw InvalidRequestBodyException("AdvanceApplicationResidenceDietAmount in DB differs from the request's one")
        if (dbAdvanceApplication.accommodationQuantity != reqAdvanceApplication.accommodationQuantity)
            throw InvalidRequestBodyException("AdvanceApplicationAccommodationQuantity in DB differs from the request's one")
        if (dbAdvanceApplication.accommodationLimit.toFloat() != reqAdvanceApplication.accommodationLimit.toFloat())
            throw InvalidRequestBodyException("AdvanceApplicationAccommodationLimit in DB differs from the request's one")
        if (dbAdvanceApplication.travelDietAmount.toFloat() != reqAdvanceApplication.travelDietAmount.toFloat())
            throw InvalidRequestBodyException("AdvanceApplicationTravelDietAmount in DB differs from the request's one")
        if (dbAdvanceApplication.travelCosts.toFloat() != reqAdvanceApplication.travelCosts.toFloat())
            throw InvalidRequestBodyException("AdvanceApplicationTravelCosts in DB differs from the request's one")
        if (dbAdvanceApplication.otherCostsDescription != reqAdvanceApplication.otherCostsDescription)
            throw InvalidRequestBodyException("AdvanceApplicationOtherCostsDescription in DB differs from the request's one")
        if (dbAdvanceApplication.otherCostsAmount != null)
            if (reqAdvanceApplication.otherCostsAmount == null ||
                    dbAdvanceApplication.otherCostsAmount.toFloat() != reqAdvanceApplication.otherCostsAmount.toFloat())
                throw InvalidRequestBodyException("AdvanceApplicationOtherCostsAmount in DB differs from the request's one")
        if (dbAdvanceApplication.residenceDietSum.toFloat() != reqAdvanceApplication.residenceDietSum.toFloat())
            throw InvalidRequestBodyException("AdvanceApplicationResidenceDietSum in DB differs from the request's one")
        if (dbAdvanceApplication.accommodationSum.toFloat() != reqAdvanceApplication.accommodationSum.toFloat())
            throw InvalidRequestBodyException("AdvanceApplicationAccommodationSum in DB differs from the request's one")
        if (dbAdvanceApplication.advanceSum.toFloat() != reqAdvanceApplication.advanceSum.toFloat())
            throw InvalidRequestBodyException("AdvanceApplicationAdvanceSum in DB differs from the request's one")
    }

    fun compareAdvancePayments(dbAdvancePaymentsInfo: AdvancePaymentsInfo, reqAdvancePaymentsInfo: AdvancePaymentsInfo) {
        if (dbAdvancePaymentsInfo.conferenceFeeValue.toFloat() != reqAdvancePaymentsInfo.conferenceFeeValue.toFloat())
            throw InvalidRequestBodyException("AdvancePaymentsConferenceFeeValue in DB differs from the request's one")
        if (dbAdvancePaymentsInfo.conferenceFeePaymentTypeSelect != reqAdvancePaymentsInfo.conferenceFeePaymentTypeSelect)
            throw InvalidRequestBodyException("AdvancePaymentsConferenceFeePaymentTypeSelect in DB differs from the request's one")
        if (dbAdvancePaymentsInfo.accommodationFeeValue.toFloat() != reqAdvancePaymentsInfo.accommodationFeeValue.toFloat())
            throw InvalidRequestBodyException("AdvancePaymentsAccommodationFeeValue in DB differs from the request's one")
        if (dbAdvancePaymentsInfo.accommodationFeePaymentTypeSelect != reqAdvancePaymentsInfo.accommodationFeePaymentTypeSelect)
            throw InvalidRequestBodyException("AdvancePaymentsAccommodationFeePaymentTypeSelect in DB differs from the request's one")
    }
}
