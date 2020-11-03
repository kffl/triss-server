package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column

data class FinancialSource(
        @Id
        @Column("id")
        val id: Int?,
        @Column("allocationAccount")
        val allocationAccount: String?,
        @Column("MPK")
        val MPK: String?,
        @Column("financialSource")
        val financialSource: String?,
        @Column("project")
        val project: String?
)
