package com.pp.trisscore.model.rows

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.*
import com.pp.trisscore.model.enums.DocumentType
import com.pp.trisscore.model.enums.PaymentType
import com.pp.trisscore.model.enums.Status
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate

@Table("ApplicationFull")
data class ApplicationFull(
        //ApplicationData
        @Id
        @Column("id")
        val id: Long?,
        @Column("firstName")
        val firstName: String,
        @Column("surname")
        val surname: String,
        @Column("birthDate")
        val birthDate: LocalDate,
        @Column("academicDegree")
        val academicDegree: String,
        @Column("phoneNumber")
        val phoneNumber: String,
        @Column("employeeId")
        val employeeId: Long?,
        @Column("identityDocumentType")
        val identityDocumentType: DocumentType,
        @Column("identityDocumentNumber")
        val identityDocumentNumber: String,
        @Column("createdOn")
        val createdOn: LocalDate?,
        @Column("placeId")
        val placeId: Long?,
        @Column("abroadStartDate")
        val abroadStartDate: LocalDate,
        @Column("abroadEndDate")
        val abroadEndDate: LocalDate,
        @Column("instituteId")
        val instituteId: Long?,
        @Column("purpose")
        val purpose: String,
        @Column("conference")
        val conference: String,
        @Column("subject")
        val subject: String,
        @Column("conferenceStartDate")
        val conferenceStartDate: LocalDate,
        @Column("conferenceEndDate")
        val conferenceEndDate: LocalDate,
        @Column("financialSourceId")
        val financialSourceId: Long?,
        @Column("abroadStartDateInsurance")
        val abroadStartDateInsurance: LocalDate,
        @Column("abroadEndDateInsurance")
        val abroadEndDateInsurance: LocalDate,
        @Column("selfInsured")
        val selfInsured: Boolean,
        @Column("advanceApplicationId")
        val advanceApplicationId: Long?,
        @Column("prepaymentId")
        val prepaymentId: Long?,
        @Column("identityDocumentID")
        val identityDocumentID: Long?,
        @Column("comments")
        val comments: String?,
        @Column("comments")
        val wildaComments: String?,
        @Column("comments")
        val directorComments: String?,
        @Column("comments")
        val rectorComments: String?,
        @Column("status")
        val status: Status?,
        //PlaceData
        @Column("pId")
        val pId: Long?,
        @Column("pCountry")
        val pCountry: String,
        @Column("pCity")
        val pCity: String,
        // InstituteData
        @Column("iId")
        val iId: Long?,
        @Column("iName")
        val iName: String,
        @Column("iActive")
        val iActive: Boolean,
        // FinancialSource Data
        @Column("fId")
        val fId: Long?,
        @Column("fAllocationAccount")
        val fAllocationAccount: String?,
        @Column("fMPK")
        val fMPK: String?,
        @Column("fFinancialSource")
        val fFinancialSource: String?,
        @Column("fProject")
        val fProject: String?,
        //AdvanceApplication DAta
        @Column("aaId")
        val aaId: Long?,
        @Column("aaPlaceId")
        val aaPlaceId: Long?,
        @Column("aaStartDate")
        val aaStartDate: LocalDate,
        @Column("aaEndDate")
        val aaEndDate: LocalDate,
        @Column("aaResidenceDietQuantity")
        val aaResidenceDietQuantity: Int,
        @Column("aaResidenceDietAmount")
        val aaResidenceDietAmount: BigDecimal,
        @Column("aaAccommodationQuantity")
        val aaAccommodationQuantity: Int,
        @Column("aaAccommodationLimit")
        val aaAccommodationLimit: BigDecimal,
        @Column("aaTravelDietAmount")
        val aaTravelDietAmount: BigDecimal,
        @Column("aaTravelCosts")
        val aaTravelCosts: BigDecimal,
        @Column("aaOtherCostsDescription")
        val aaOtherCostsDescription: String?,
        @Column("aaOtherCostsAmount")
        val aaOtherCostsAmount: BigDecimal?,
        @Column("aaResidenceDietSum")
        val aaResidenceDietSum: BigDecimal,
        @Column("aaAccommodationSum")
        val aaAccommodationSum: BigDecimal,
        @Column("aaAdvanceSum")
        val aaAdvanceSum: BigDecimal,
        //accomodationFeeData
        @Column("paId")
        val paId: Long?,
        @Column("paAmount")
        val paAmount: BigDecimal,
        @Column("paPaymentType")
        val paPaymentType: PaymentType,
        //conferenceFeeData
        @Column("pcId")
        val pcId: Long?,
        @Column("pcAmount")
        val pcAmount: BigDecimal,
        @Column("pcPaymentType")
        val pcPaymentType: PaymentType
) {
    fun getApplication() = Application(
            id, firstName, surname, birthDate,
            academicDegree, phoneNumber, employeeId,
            identityDocumentType, identityDocumentNumber, createdOn, placeId,
            abroadStartDate, abroadEndDate,
            instituteId, purpose, conference,
            subject, conferenceStartDate,
            conferenceEndDate, financialSourceId,
            abroadStartDateInsurance, abroadEndDateInsurance,
            selfInsured, advanceApplicationId, prepaymentId,
            comments, wildaComments,
            directorComments, rectorComments, status
    )

    fun getPlace() = Place(pId, pCountry, pCity)

    fun getInstitute() = Institute(
            iId, iName, iActive
    )

    fun getFinancialSource() = FinancialSource(
            fId, fAllocationAccount, fMPK, fFinancialSource, fProject
    )

    fun getAdvanceApplication() = AdvanceApplication(
            aaId, aaPlaceId, aaStartDate, aaEndDate,
            aaResidenceDietQuantity, aaResidenceDietAmount,
            aaAccommodationQuantity, aaAccommodationLimit,
            aaTravelDietAmount, aaTravelCosts, aaOtherCostsDescription,
            aaOtherCostsAmount, aaResidenceDietSum, aaAccommodationSum,
            aaAdvanceSum
    )

    fun getAdvancePayments() = AdvancePaymentsInfo(
            pcAmount, pcPaymentType, paAmount, paPaymentType
    )

    fun getApplicationInfo(transportList: List<Transport>) = ApplicationInfo(getInstitute(), getPlace(),
            getApplication(), getFinancialSource(),
            transportList, getAdvanceApplication(),
            getAdvancePayments())
}