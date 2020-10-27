package com.pp.trisscore.model.rows

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Table("ApplicationRow")
data class ApplicationRow(
        @Id
        @Column("id")
        val id: Long?,
        @Column("employeeId")
        val employeeId: Long?,
        @Column("country")
        val country: String?,
        @Column("city")
        val city: String?,
        @Column("abroadStartDate")
        val abroadStartDate: Date?,
        @Column("abroadEndDate")
        val abroadEndDate: Date?,
        @Column("status")
        val status: String?
)