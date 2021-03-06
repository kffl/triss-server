package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
@Table("Application")
data class Application(
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
        val identityDocumentType: Long,
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
        val abroadStartDateInsurance: LocalDate?,
        @Column("abroadEndDateInsurance")
        val abroadEndDateInsurance: LocalDate?,
        @Column("selfInsured")
        val selfInsured:Boolean,
        @Column("advanceApplicationId")
        val advanceApplicationId: Long?,
        @Column("prepaymentId")
        val prepaymentId: Long?,
        @Column("comments")
        val comments: String?,
        @Column("wildaComments")
        val wildaComments: String?,
        @Column("directorComments")
        val directorComments: String?,
        @Column("rectorComments")
        val rectorComments: String?,
        @Column("status")
        val status: Long?
)
