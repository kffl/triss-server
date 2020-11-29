package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.Role
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date
import java.time.LocalDate

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
@Table("Employee")
data class Employee (
        @Id
        @Column("id")
        val id: Long?,
        @Column("employeeId")
        val employeeId: Long?,
        @Column("firstName")
        val firstName: String,
        @Column("surname")
        val surname: String,
        @Column("birthDate")
        val birthDate: LocalDate,
        @Column("phoneNumber")
        val phoneNumber: String,
        @Column("academicDegree")
        val academicDegree: String,
        @Column("instituteID")
        val instituteID: Long?,
        @Column("employeeType")
        val employeeType: Role?
)
