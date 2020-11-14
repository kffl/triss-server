package com.pp.trisscore.model.rows

import com.pp.trisscore.model.enums.PaymentType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate

@Table("ApplicationFull")
data class ApplicationFull(
        @Id
        @Column("id")
        val id: Long,
        @Column("firstName")
        val firstName: String,
        @Column("surname")
        val surname: String,
        @Column("birthDate")
        val birthDate: LocalDate,
        @Column("academicDegree")
        val academicTitle: String,
        @Column("phoneNumber")
        val phoneNumber: String,
        @Column("city")
        val destinationCity: String,
        @Column("country")
        val destinationCountry: String,
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
        @Column("abroadStartDate")
        val abroadStartDate: LocalDate,
        @Column("abroadEndDate")
        val abroadEndDate: LocalDate,
        @Column("selfInsured")
        val selfInsuredCheckbox: Boolean,
        @Column("requestPaymentStartDate")
        val requestPaymentStartDate: LocalDate,
        @Column("requestPaymentEndDate")
        val requestPaymentEndDate: LocalDate,
        @Column("requestPaymentDays")
        val requestPaymentDays: Int,
        @Column("requestPaymentDaysAmount")
        val requestPaymentDaysAmount: BigDecimal,
        @Column("requestPaymentAccommodation")
        val requestPaymentAccommodation: Int,
        @Column("requestPaymentAccommodationLimit")
        val requestPaymentAccommodationLimit: BigDecimal,
        @Column("requestPaymentTravelDiet")
        val requestPaymentTravelDiet: BigDecimal,
        @Column("requestPaymentLocalTransportCosts")
        val requestPaymentLocalTransportCosts: BigDecimal,
        @Column("requestPaymentOtherExpensesDescription")
        val requestPaymentOtherExpensesDescription: String?,
        @Column("requestPaymentOtherExpensesValue")
        val requestPaymentOtherExpensesValue: BigDecimal?,
        @Column("requestPaymentDaysAmountSum")
        val requestPaymentDaysAmountSum: BigDecimal,
        @Column("requestPaymentAccommodationSum")
        val requestPaymentAccommodationSum:BigDecimal,
        @Column("requestPaymentSummarizedCosts")
        val requestPaymentSummarizedCosts: BigDecimal,
        @Column("conferenceFeeValue")
        val conferenceFeeValue: BigDecimal,
        @Column("conferenceFeePaymentTypeSelect")
        val conferenceFeePaymentTypeSelect: PaymentType,
        @Column("depositValue")
        val depositValue: BigDecimal,
        @Column("depositPaymentTypeSelect")
        val depositPaymentTypeSelect: PaymentType,
        @Column("identityID")
        val identityID:Long,
        @Column("employeeID")
        val employeeID: Long,
        @Column("type")
        val type: Int,
        @Column("number")
        val number: String,
        @Column("allocationAccount")
        val allocationAccount: String?,
        @Column("MPK")
        val MPK: String?,
        @Column("financialSourceId")
        val financialSourceId: Long?,
        @Column("financialSource")
        val financialSource: String?,
        @Column("project")
        val project: String?,
        @Column("comments")
        val comments: String?


)