package com.pp.trisscore.model

import java.util.*

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
data class Place(
        val id: Int,
        val country: String, //TODO probably we wnat to have this in db so it should be Country class with one or maybe two fields (name and code)
        val city: String, // - || -
        val address: String
)