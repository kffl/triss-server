package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.DocumentType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
@Table("IdentityDocument")
data class IdentityDocument (
        @Id
        @Column("id")
        val id: Long?,
        @Column("employeeId")
        val employeeId: Long?,
        @Column("type")
        val type: Int,
        @Column("number")
        val number: String
)
