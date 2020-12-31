package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import java.math.BigDecimal

data class SettlementElement (
    @Id
    @Column("id")
    val id:Long?,
    @Column("settlementApplicationId")
    val settlementApplicationId:String,
    @Column("documentNumber")
    val documentNumber:Long,
    @Column("value")
    val value:BigDecimal,
    @Column("comment")
    val comment:Long,
    @Column("scanLoc")
    val scanLoc:String
    )
