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

    fun compareApplicationsInfo(dbApplicationInfo: ApplicationInfo, reqApplicationInfo: ApplicationInfo, role: Role) {
        compareApplications(role, dbApplicationInfo.application, reqApplicationInfo.application)
        compareInstitutes(dbApplicationInfo.institute, reqApplicationInfo.institute)
        if (role != Role.DIRECTOR)
            compareFinancialSources(dbApplicationInfo.financialSource!!, reqApplicationInfo.financialSource!!)
        compareAdvanceApplication(dbApplicationInfo.advanceApplication, reqApplicationInfo.advanceApplication)
        compareAdvancePayments(dbApplicationInfo.advancePayments, reqApplicationInfo.advancePayments)
    }

    fun compareInstitutes(dbInstitute: Institute, reqInstitute: Institute) {
        if (dbInstitute.copy(active = reqInstitute.active) != reqInstitute)
            throw InvalidRequestBodyException("Institute in DB differs from the request's Institute ")
    }

    fun comparePlaces(dbPlace: Place, reqPlace: Place) {
        if (dbPlace != reqPlace)
            throw InvalidRequestBodyException("Place in DB differs from the request's Place")
    }

    private fun compareApplications(role: Role,dbApplication: Application, reqApplication: Application) {
        when (role) {
            Role.WILDA -> {
                if (dbApplication.copy(wildaComments = reqApplication.wildaComments) != reqApplication.copy(status = Status.WaitingForWilda))
                    throw InvalidRequestBodyException("ApplicationFinancialSourceId in DB differs from the request's one")
            }
            Role.DIRECTOR -> {
                if (dbApplication.copy(directorComments = reqApplication.directorComments) != reqApplication.copy(status = Status.WaitingForDirector))
                    throw InvalidRequestBodyException("ApplicationFinancialSourceId in DB differs from the request's one")
            }
            Role.RECTOR -> {
                if (dbApplication.copy(rectorComments = reqApplication.rectorComments) != reqApplication.copy(status = Status.WaitingForRector))
                    throw InvalidRequestBodyException("ApplicationFinancialSourceId in DB differs from the request's one")
            }
            Role.USER -> {
                throw UnauthorizedException("You do not have permission to perform this action")
            }
        }
    }

    fun compareFinancialSources(dbFinancialSource: FinancialSource, reqFinancialSource: FinancialSource) {
        if (dbFinancialSource != reqFinancialSource)
            throw InvalidRequestBodyException("FinancialSource in DB differs from the request's one")
    }

    fun compareTransports(dbTransport: Transport, reqTransport: Transport) {
        if (dbTransport != reqTransport)
            throw InvalidRequestBodyException("Transport in DB differs from the request's one")
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
