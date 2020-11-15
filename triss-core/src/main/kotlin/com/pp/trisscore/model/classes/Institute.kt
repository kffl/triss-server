package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("Institute")
data class Institute (
        @Id
        @Column("id")
        val id: Long?,
        @Column("name")
        val name: String
)