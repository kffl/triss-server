package com.pp.trisscore.model.applicationinfoelements

import java.sql.Date

data class BasicInfo (
        val firstName: String,
        val surname:String,
        val academicTitle:String,
        val phoneNumber:String,
        val destinationCity: String,
        val destinationCountry: String,
        val abroadStartDate:Date,
        val abroadEndDate:Date,
        val purpose: String,
        val conference: String,
        val subject : String,
        val conferenceStartDate:Date,
        val conferenceEndDate:Date
)
