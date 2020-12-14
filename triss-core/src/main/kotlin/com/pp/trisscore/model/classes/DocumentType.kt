package com.pp.trisscore.model.classes

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("DocumentType")
data class DocumentType(
        @Id
        @Column("id")
        val id: Long?,
        @Column("namePl")
        val namePl: String,
        @Column("nameEng")
        val nameEng: String
)
