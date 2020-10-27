package com.pp.trisscore.model.classes

import com.pp.trisscore.model.classes.Fee
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
@Table("Prepayment")
data class Prepayment (
        @Id
        @Column("id")
        val id: Long,
        @Column("conferenceFeeId")
        val conferenceFeeId: Long,
        //val conferenceFee: Fee?,
        @Column("accommodationFeeId")
        val accommodationFeeId: Long
        //val accommodation: Fee?
)