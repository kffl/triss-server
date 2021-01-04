package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

@Table("SettlementElement")
data class SettlementElement(
    @Id
    @Column("id")
    val id:Long?,
    @Column("settlementApplicationId")
    val settlementApplicationId:Long,
    @Column("documentNumber")
    val documentNumber:String,
    @Column("value")
    val value:BigDecimal,
    @Column("comment")
    val comment:String,
    @Column("scanLoc")
    val scanLoc: String?
    )
