package com.pp.trisscore.model.enums

/**
 *
 * author: Marceli Jerzyński, Mateusz Tamborski
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 * Update date: 28.10.2020
 **/

enum class Vehicle(val namePl: String, val nameEng: String) {
    Plane("Samolot", "Plane"),
    Car("Samochód", "Car"),
    Bus("Autobus","Bus"),
    Train("Pociąg","Train"),
    Other("Inny", "Other")
}