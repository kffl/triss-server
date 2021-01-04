package com.pp.trisscore.model.rows

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate


@Table("FullSettlementElement")
data class FullSettlementElement (
    @Column("settlementApplicationId")
    val settlementApplicationId:Long,
    @Column("applicationId")
    val applicationId:Long,
    @Column("creatorId")
    val creatorId:Long,
    @Column("status")
    val status:Long,
    @Column("lastModifiedDate")
    val lastModifiedDate: LocalDate,
    @Column("settlementElementId")
    val settlementElementId:Long,
    @Column("documentNumber")
    val documentNumber:String,
    @Column("value")
    val value: BigDecimal,
    @Column("comment")
    val comment:String,
    @Column("scanLoc")
    val scanLoc:String
)
