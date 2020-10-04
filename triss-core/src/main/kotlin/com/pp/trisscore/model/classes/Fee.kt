package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.PaymentType

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
data class Fee (
        val amount: Double,
        val paymentType: PaymentType
)