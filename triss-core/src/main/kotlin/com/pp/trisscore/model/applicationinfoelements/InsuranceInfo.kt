package com.pp.trisscore.model.applicationinfoelements

import java.sql.Date

data class InsuranceInfo (
    val firstName :String,
    val surname :String,
    val birthDate : Date,
    val departureCountry : String,
    val abroadStartDateInsurance : Date,
    val abroadEndDateInsurance : Date,
    val selfInsuredCheckbox : Boolean
)
