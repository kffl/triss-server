package com.pp.trisscore.model.rows

import com.pp.trisscore.model.enums.Status
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
        @Column("firstName")
        val firstName: String?,
        @Column("surname")
        val surname: String?,
        @Column("employeeId")
        val employeeId: Long?,
        @Column("instituteName")
        val instituteName: String?,
        @Column("instituteId")
        val instituteId: Long?,
        @Column("country")
        val country: String?,
        @Column("city")
        val city: String?,
        @Column("abroadStartDate")
        val abroadStartDate: Date?,
        @Column("abroadEndDate")
        val abroadEndDate: Date?,
        @Column("status")
        val status: Status?
)