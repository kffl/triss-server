package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.PaymentType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
@Table("PrepaymentFee")
data class PrepaymentFee (
        @Id
        @Column("id")
        val id: Long?,
        @Column("amount")
        val amount: BigDecimal,
        @Column("paymentType")
        val paymentType: PaymentType
)