package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.Vehicle
import java.sql.Date
import java.time.LocalDateTime

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
data class Request(
        val id: Long,
        val employeeId: Long,
        val employee: Employee,
        val createdOn: Date,
        val placeId: Long,
        val place: Place,
        val conferenceStartDate: Date,
        val conferenceEndDate: Date,
        val purpose: String,
        val description: String,
        val subject: String,
        val financialSourceId: Long,
        val financialSource: FinancialSource,
        val vehicleList: String,
        val vehicle: List<Vehicle>,
        val routeList: String,
        val route: List<String>,
        val departureTime: LocalDateTime,
        val carrier: String,
        val abroadStartDate: Date?,
        val abroadEndDate: Date?,
        val selfInsured: Boolean,
        val advanceRequestId: Long,
        val advanceRequest: AdvanceRequest,
        val prepaymentId: Long,
        val prepayment: Prepayment,
        val identityDocument: IdentityDocument,
        val comments: String
)