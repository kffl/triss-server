package com.pp.trisscore.model.classes

import com.pp.trisscore.model.classes.Fee

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
data class Prepayment (
        val conferenceFee: Fee?,
        val accommodation: Fee?
)