package com.pp.trisscore.model

import java.sql.Date

data class Employee (
        val id: Int,
        val firstName: String,
        val surname: String,
        val birthDate: Date,
        val birthPlace: String,
        val documents: List<IdentityDocument>,
        val requests: List<Request>
)