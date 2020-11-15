package com.pp.trisscore.model.rows

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.classes.*
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
        @Column("createdOn")
        val createdOn: LocalDate?,
        @Column("abroadStartDate")
        val abroadStartDate: LocalDate,
        @Column("abroadEndDate")
        val abroadEndDate: LocalDate,
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
        @Column("abroadStartDateInsurance")
        val abroadStartDateInsurance: LocalDate,
        @Column("abroadEndDateInsurance")
        val abroadEndDateInsurance: LocalDate,
        @Column("selfInsured")
        val selfInsured: Boolean,
        @Column("comments")
        val comments: String?,
        @Column("status")
        val status: Status?,
        //InstituteData
        @Column("name")
        val name: String,
        //EmployeeData
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
        //PlaceData
        @Column("country")
        val country: String,
        @Column("city")
        val city: String,
        //FinancialSourceData
        @Column("allocationAccount")
        val allocationAccount: String?,
        @Column("MPK")
        val MPK: String?,
        @Column("financialSource")
        val financialSource: String?,
        @Column("project")
        val project: String?,
        //AdvanceApplicationData
        @Column("startDate")
        val startDate: LocalDate,
        @Column("endDate")
        val endDate: LocalDate,
        @Column("residenceDietQuantity")
        val residenceDietQuantity: Int,
        @Column("residenceDietAmount")
        val residenceDietAmount: BigDecimal,
        @Column("accommodationQuantity")
        val accommodationQuantity: Int,
        @Column("accommodationLimit")
        val accommodationLimit: BigDecimal,
        @Column("travelDietAmount")
        val travelDietAmount: BigDecimal,
        @Column("travelCosts")
        val travelCosts: BigDecimal,
        @Column("otherCostsDescription")
        val otherCostsDescription: String?,
        @Column("otherCostsAmount")
        val otherCostsAmount: BigDecimal?,
        @Column("residenceDietSum")
        val residenceDietSum: BigDecimal,
        @Column("accommodationSum")
        val accommodationSum: BigDecimal,
        @Column("advanceSum")
        val advanceSum: BigDecimal,
        //AdvancePaymentsData
        @Column("conferenceFeeValue")
        val conferenceFeeValue: BigDecimal,
        @Column("conferenceFeePaymentTypeSelect")
        val conferenceFeePaymentTypeSelect: PaymentType,
        @Column("accommodationFeeValue")
        val accommodationFeeValue: BigDecimal,
        @Column("accommodationFeePaymentTypeSelect")
        val accommodationFeePaymentTypeSelect: PaymentType,
        //IdentityDocumentData
        @Column("type")
        val type: Int,
        @Column("number")
        val number: String

) {

    fun getApplication() = Application(id = id, employeeId = null,
            createdOn = createdOn, placeId = null,
            abroadStartDate = abroadStartDate, abroadEndDate = abroadEndDate,
            instituteId = null, purpose = purpose,
            conference = conference, subject = subject,
            conferenceStartDate = conferenceStartDate, conferenceEndDate = conferenceEndDate,
            financialSourceId = null, abroadStartDateInsurance = abroadStartDateInsurance,
            abroadEndDateInsurance = abroadEndDateInsurance, selfInsured = selfInsured,
            advanceRequestId = null, prepaymentId = null,
            identityDocumentID = null, comments = comments,
            status = status)

    fun getInstitute() = Institute(id = null, name = name)

    fun getPlace() = Place(id = null, country = country, city = city)

    fun getEmployee() = Employee(firstName = firstName,
            surname = surname, birthDate = birthDate,
            academicDegree = academicDegree, phoneNumber = phoneNumber,
            id = null, instituteID = null,
            employeeTypeID = null)

    fun getFinancialSource() = FinancialSource(
            id = null, allocationAccount = allocationAccount,
            MPK = MPK, financialSource = financialSource,
            project = project
    )

    fun getAdvanceApplication() = AdvanceApplication(
            id = null, placeId = null, startDate = startDate,
            endDate = endDate, residenceDietQuantity = residenceDietQuantity,
            residenceDietAmount = residenceDietAmount, accommodationQuantity = accommodationQuantity,
            accommodationLimit = accommodationLimit, travelDietAmount = travelDietAmount,
            travelCosts = travelCosts, otherCostsAmount = otherCostsAmount,
            otherCostsDescription = otherCostsDescription, residenceDietSum = residenceDietSum,
            accommodationSum = accommodationSum, advanceSum = advanceSum)

    fun getAdvancePaymentsInfo() = AdvancePaymentsInfo(
            conferenceFeeValue = conferenceFeeValue,
            conferenceFeePaymentTypeSelect = conferenceFeePaymentTypeSelect,
            accommodationFeePaymentTypeSelect = accommodationFeePaymentTypeSelect,
            accommodationFeeValue = accommodationFeeValue
    )
        fun getIdentityDocument() = IdentityDocument(
                id = null,
                employeeID =null,
                type = type,
                number = number
        )
}