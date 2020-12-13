package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.RequestDataDiffersFromDatabaseDataException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.Role
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
            throw RequestDataDiffersFromDatabaseDataException("Institute")
    }

    fun comparePlaces(dbPlace: Place, reqPlace: Place) {
        if (dbPlace != reqPlace)
            throw RequestDataDiffersFromDatabaseDataException("Place")
    }

    private fun compareApplications(role: Role,dbApplication: Application, reqApplication: Application) {
        when (role) {
            Role.WILDA -> {
                if (dbApplication.copy(wildaComments = reqApplication.wildaComments) != reqApplication)
                    throw RequestDataDiffersFromDatabaseDataException("ApplicationFinancialSourceId")
            }
            Role.DIRECTOR -> {
                if (dbApplication.copy(directorComments = reqApplication.directorComments) != reqApplication)
                    throw RequestDataDiffersFromDatabaseDataException("ApplicationFinancialSourceId")
            }
            Role.RECTOR -> {
                if (dbApplication.copy(rectorComments = reqApplication.rectorComments) != reqApplication)
                    throw RequestDataDiffersFromDatabaseDataException("ApplicationFinancialSourceId")
            }
            Role.USER -> {
                throw UnauthorizedException("You do not have permission to perform this action")
            }
        }
    }

    fun compareFinancialSources(dbFinancialSource: FinancialSource, reqFinancialSource: FinancialSource) {
        if (dbFinancialSource != reqFinancialSource)
            throw RequestDataDiffersFromDatabaseDataException("FinancialSource")
    }

    fun compareTransports(dbTransport: Transport, reqTransport: Transport) {
        if (dbTransport != reqTransport)
            throw RequestDataDiffersFromDatabaseDataException("Transport")
    }

    fun compareAdvanceApplication(dbAdvanceApplication: AdvanceApplication, reqAdvanceApplication: AdvanceApplication) {
        if (dbAdvanceApplication.id != reqAdvanceApplication.id)
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationId")
        if (dbAdvanceApplication.placeId != reqAdvanceApplication.placeId)
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationPlaceId")
        if (dbAdvanceApplication.startDate != reqAdvanceApplication.startDate)
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationStartDate")
        if (dbAdvanceApplication.endDate != reqAdvanceApplication.endDate)
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationEndDate")
        if (dbAdvanceApplication.residenceDietQuantity != reqAdvanceApplication.residenceDietQuantity)
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationResidenceDietQuantity")
        if (dbAdvanceApplication.residenceDietAmount.toFloat() != reqAdvanceApplication.residenceDietAmount.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationResidenceDietAmount")
        if (dbAdvanceApplication.accommodationQuantity != reqAdvanceApplication.accommodationQuantity)
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationAccommodationQuantity")
        if (dbAdvanceApplication.accommodationLimit.toFloat() != reqAdvanceApplication.accommodationLimit.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationAccommodationLimit")
        if (dbAdvanceApplication.travelDietAmount.toFloat() != reqAdvanceApplication.travelDietAmount.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationTravelDietAmount")
        if (dbAdvanceApplication.travelCosts.toFloat() != reqAdvanceApplication.travelCosts.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationTravelCosts")
        if (dbAdvanceApplication.otherCostsDescription != reqAdvanceApplication.otherCostsDescription)
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationOtherCostsDescription")
        if (dbAdvanceApplication.otherCostsAmount != null)
            if (reqAdvanceApplication.otherCostsAmount == null ||
                    dbAdvanceApplication.otherCostsAmount.toFloat() != reqAdvanceApplication.otherCostsAmount.toFloat())
                throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationOtherCostsAmount")
        if (dbAdvanceApplication.residenceDietSum.toFloat() != reqAdvanceApplication.residenceDietSum.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationResidenceDietSum")
        if (dbAdvanceApplication.accommodationSum.toFloat() != reqAdvanceApplication.accommodationSum.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationAccommodationSum")
        if (dbAdvanceApplication.advanceSum.toFloat() != reqAdvanceApplication.advanceSum.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvanceApplicationAdvanceSum")
    }

    fun compareAdvancePayments(dbAdvancePaymentsInfo: AdvancePaymentsInfo, reqAdvancePaymentsInfo: AdvancePaymentsInfo) {
        if (dbAdvancePaymentsInfo.conferenceFeeValue.toFloat() != reqAdvancePaymentsInfo.conferenceFeeValue.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvancePaymentsConferenceFeeValue")
        if (dbAdvancePaymentsInfo.conferenceFeePaymentTypeSelect != reqAdvancePaymentsInfo.conferenceFeePaymentTypeSelect)
            throw RequestDataDiffersFromDatabaseDataException("AdvancePaymentsConferenceFeePaymentTypeSelect")
        if (dbAdvancePaymentsInfo.accommodationFeeValue.toFloat() != reqAdvancePaymentsInfo.accommodationFeeValue.toFloat())
            throw RequestDataDiffersFromDatabaseDataException("AdvancePaymentsAccommodationFeeValue")
        if (dbAdvancePaymentsInfo.accommodationFeePaymentTypeSelect != reqAdvancePaymentsInfo.accommodationFeePaymentTypeSelect)
            throw RequestDataDiffersFromDatabaseDataException("AdvancePaymentsAccommodationFeePaymentTypeSelect")
    }
}
