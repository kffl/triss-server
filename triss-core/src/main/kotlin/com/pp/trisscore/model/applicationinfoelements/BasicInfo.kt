package com.pp.trisscore.model.applicationinfoelements

import java.sql.Date

data class BasicInfo (
    val firstName: String,
    val surName:String,
    val academicTitle:String,
    val phoneNumber:String,
    val destination: String,
    val abroadStartDate:Date,
    val abroadEndDate:Date,
    val purpose: String,
    val conference: String,
    val subject : String,
    val conferenceStartDate:Date,
    val conferenceEndDate:Date
    )
