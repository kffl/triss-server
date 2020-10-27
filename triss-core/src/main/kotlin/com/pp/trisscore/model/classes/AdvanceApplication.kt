package com.pp.trisscore.model.classes

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
@Table("AdvanceApplication")
data class AdvanceApplication (
        @Id
        @Column("id")
        val id: Int,
        @Column("placeId")
        val placeId: Long,
        //val place: Place,
        @Column("startDate")
        val startDate: Date,
        @Column("endDate")
        val endDate: Date,
        @Column("residenceDietQuantity")
        val residenceDietQuantity: Int,
        @Column("residenceDietAmount")
        val residenceDietAmount: Double,
        @Column("accommodationQuantity")
        val accommodationQuantity: Int,
        @Column("limit")
        val limit: List<Double>, //TODO: czym jest limit i jak to wrzucić do DB
        @Column("travelDietAmount")
        val travelDietAmount: Double,
        @Column("travelCosts")
        val travelCosts: Double,
        @Column("otherCostsDescription")
        val otherCostsDescription: String,
        @Column("otherCostsAmount")
        val otherCostsAmount: Double,
        @Column("advanceSum")
        val advanceSum: Double
)
