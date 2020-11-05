package com.pp.trisscore.model.applicationinfoelements

import java.sql.Date
import java.time.LocalDate

data class InsuranceInfo (
        val firstName :String,
        val surname :String,
        val birthDate : LocalDate,
        val departureCountry : String,
        val abroadStartDateInsurance : LocalDate,
        val abroadEndDateInsurance : LocalDate,
        val selfInsuredCheckbox : Boolean
)
