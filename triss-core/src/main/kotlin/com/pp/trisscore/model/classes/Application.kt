package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.Vehicle
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import java.time.LocalDateTime

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
        val id: Long,
        @Column("employeeId")
        val employeeId: Long,
//        val employee: Employee,
        @Column("createdOn")
        val createdOn: Date,
        @Column("placeId")
        val placeId: Long,
//        val place: Place,
        @Column("conferenceStartDate")
        val conferenceStartDate: Date,
        @Column("conferenceEndDate")
        val conferenceEndDate: Date,
        @Column("purpose")
        val purpose: String,
        @Column("description")
        val description: String,
        @Column("subject")
        val subject: String,
        @Column("financialSourceId")
        val financialSourceId: Long,
//        val financialSource: FinancialSource,
        @Column("vehicleList")
        val vehicleList: String,
        //val vehicle: List<Vehicle>,
        @Column("routeList")
        val routeList: String,
        //val route: List<String>,
        @Column("departureTime")
        val departureTime: LocalDateTime,
        @Column("carrier")
        val carrier: String,
        @Column("abroadStartDate")
        val abroadStartDate: Date?,
        @Column("abroadEndDate")
        val abroadEndDate: Date?,
        @Column("selfInsured")
        val selfInsured: Boolean,
        @Column("advanceRequestId")
        val advanceRequestId: Long,
//        val advanceApplication: AdvanceApplication,
        @Column("prepaymentId")
        val prepaymentId: Long,
//        val prepayment: Prepayment,
        @Column("identityDocumentID")
        val identityDocumentID: Long,
        @Column("comments")
        val comments: String,
        @Column("status")
        val status: String
)
