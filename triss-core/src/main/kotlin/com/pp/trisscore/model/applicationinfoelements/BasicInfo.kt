package com.pp.trisscore.model.applicationinfoelements

import java.sql.Date
import java.time.LocalDate

data class BasicInfo (
        val firstName: String,
        val surname:String,
        val academicTitle:String,
        val phoneNumber:String,
        val destinationCity: String,
        val destinationCountry: String,
        val abroadStartDate:LocalDate,
        val abroadEndDate:LocalDate,
        val purpose: String,
        val conference: String,
        val subject : String,
        val conferenceStartDate:LocalDate,
        val conferenceEndDate:LocalDate
)
