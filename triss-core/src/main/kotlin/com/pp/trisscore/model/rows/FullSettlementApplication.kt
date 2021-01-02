package com.pp.trisscore.model.rows

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate

@Table("FullSettlementApplication")
data class FullSettlementApplication(
    @Id
    @Column("id")
    val id: Long?,
    @Column("applicationId")
    val applicationId: Long,
    @Column("creatorId")
    val creatorId: Long,
    @Column("status")
    val status: Long,
    @Column("wildaComments")
    val wildaComments: String?,
    @Column("lastModifiedDate")
    val lastModifiedDate: LocalDate,
    @Column("abroadStartDate")
    val abroadStartDate: LocalDate,
    @Column("abroadEndDate")
    val abroadEndDate: LocalDate,
    @Column("purpose")
    val purpose: String,
    @Column("conference")
    val conference: String,
    @Column("subject")
    val subject: String,
    @Column("aaAdvanceSum")
    val aaAdvanceSum: BigDecimal,
    @Column("settlementSum")
    val settlementSum: BigDecimal,
    @Column("elementCount")
    val elementCount: Int,
    @Column("returnSum")
    val returnSum: BigDecimal
)
