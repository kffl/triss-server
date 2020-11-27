package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table


@Table("FinancialSource")
data class FinancialSource(
        @Id
        @Column("id")
        val id: Long?,
        @Column("allocationAccount")
        val allocationAccount: String?,
        @Column("MPK")
        val mpk: String?,
        @Column("financialSourceValue")
        val financialSource: String?,
        @Column("project")
        val project: String?
)
