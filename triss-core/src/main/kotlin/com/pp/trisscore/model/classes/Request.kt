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
        val id: Int,
        val employeeId: Int,
        val employee: Employee,
        val createdOn: Date,
        val place: Place,
        val conferenceStartDate: Date,
        val conferenceEndDate: Date,
        val purpose: String,
        val description: String,
        val subject: String,
        val financialSource: FinancialSource,
        val vehicle: List<Vehicle>,
        val route: List<String>,
        val departureTime: LocalDateTime,
        val carrier: String,
        val abroadStartDate: Date?,
        val abroadEndDate: Date?,
        val selfInsured: Boolean,
        val advanceRequest: AdvanceRequest,
        val prepayment: Prepayment,
        val identityDocument: IdentityDocument,
        val comments: String
)