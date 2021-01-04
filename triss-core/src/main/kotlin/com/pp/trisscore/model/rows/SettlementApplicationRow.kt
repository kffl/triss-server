package com.pp.trisscore.model.rows

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Date

@Table("SettlementApplicationRow")
data class SettlementApplicationRow(
    @Id
    @Column("id")
    val id: Long?,
    @Column("applicationId")
    val applicationId: Long?,
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
    val status: Long?,
    @Column("statusEng")
    val statusEng: String?,
    @Column("statusPl")
    val statusPl: String?
)
