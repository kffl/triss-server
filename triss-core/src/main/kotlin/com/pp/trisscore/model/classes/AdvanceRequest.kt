package com.pp.trisscore.model.classes

import java.sql.Date

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
data class AdvanceRequest (
        val id: Int,
        val place: Place,
        val startDate: Date,
        val endDate: Date,
        val residenceDietQuantity: Int,
        val residenceDietAmount: Double,
        val accommodationQuantity: Int,
        val limit: List<Double>,
        val travelDietAmount: Double,
        val travelCosts: Double,
        val otherCostsDescription: String,
        val otherCostsAmount: Double,
        val advanceSum: Double
)