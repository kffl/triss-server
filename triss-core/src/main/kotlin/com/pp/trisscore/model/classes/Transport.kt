package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.Vehicle
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import java.time.LocalDate

@Table("Transport")
data class Transport(
        @Id
        @Column("id")
        val id: Long?,
        @Column("applicationID")
        val applicationID: Long,
        @Column("destinationFrom")
        val destinationFrom: String,
        @Column("destinationTo")
        val destinationTo: String,
        @Column("departureDay")
        val departureDay: LocalDate,
        @Column("departureMinute")
        val departureMinute: Int,
        @Column("departureHour")
        val departureHour: Int,
        @Column("vehicleSelect")
        val vehicleSelect:Vehicle,
        @Column("carrier")
        val carrier: String
)