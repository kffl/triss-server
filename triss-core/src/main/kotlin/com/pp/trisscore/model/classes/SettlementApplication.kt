package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate


@Table("SettlementApplication")
data class SettlementApplication(
    @Id
    @Column("id")
    val id:Long?,
    @Column("applicationId")
    val applicationId:Long,
    @Column("status")
    val status:Long,
    @Column("lastModifiedDate")
    val lastModifiedDate:LocalDate
    )
