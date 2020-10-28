package com.pp.trisscore.model.enums

/**
 *
 * author: Marceli Jerzyński, Mateusz Tamborski
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 * Update date: 28.10.2020
 **/

enum class DocumentType (val namePl: String, val nameEng: String) {
    IdCard("Dowód osobisty","ID card"),
    Passport("Paszport","Passport"),
    DriverLicense("Prawo jazdy","Driving license"),
    Other("Inne","Other")
}
