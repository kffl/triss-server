package com.pp.trisscore.model.classes

import java.sql.Date
/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
data class Employee (
        val id: Int,
        val firstName: String,
        val surname: String,
        val birthDate: Date,
        val birthPlace: String,
        val documents: List<IdentityDocument>,
        val requests: List<Request>
)