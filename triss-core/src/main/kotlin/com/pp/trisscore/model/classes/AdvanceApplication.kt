package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.sql.Date
import java.time.LocalDate

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
        val id: Long?,
        @Column("placeId")
        val placeId: Long?,
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
        val advanceSum: BigDecimal
)
