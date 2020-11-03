package com.pp.trisscore.model.classes

import java.sql.Date

data class Transport (
    var id:Int?,
    val destinationFrom:String,
    val destinationTo:String,
    val departureDay:Date,
    val departureHour:Int,
    val departureMinute:Int,
    val carrier:String
)
