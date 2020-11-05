package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.Status
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date

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
        @Column("employeeId")
        val employeeId: Long,
        @Column("createdOn")
        val createdOn: Date,
        @Column("placeId")
        val placeId: Long,
        @Column("abroadStartDate")
        val abroadStartDate: Date,
        @Column("abroadEndDate")
        val abroadEndDate: Date,
        @Column("purpose")
        val purpose: String,
        @Column("conference")
        val conference: String,
        @Column("subject")
        val subject: String,
        @Column("conferenceStartDate")
        val conferenceStartDate: Date,
        @Column("conferenceEndDate")
        val conferenceEndDate: Date,
        @Column("financialSourceId")
        val financialSourceId: Long,
        @Column("abroadStartDateInsurance")
        val abroadStartDateInsurance: Date,
        @Column("abroadEndDateInsurance")
        val abroadEndDateInsurance: Date,
        @Column("selfInsured")
        val selfInsured:Boolean,
        @Column("advanceRequestId")
        val advanceRequestId: Long,
        @Column("prepaymentId")
        val prepaymentId: Long,
        @Column("identityDocumentID")
        val identityDocumentID: Long,
        @Column("comments")
        val comments: String,
        @Column("status")
        val status: Status
)
