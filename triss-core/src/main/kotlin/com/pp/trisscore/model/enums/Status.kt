package com.pp.trisscore.model.enums

import com.fasterxml.jackson.annotation.JsonFormat

/**
 *
 * author: Marceli Jerzyński, Mateusz Tamborski
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 * Update date: 28.10.2020
 **/

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
enum class Status(val namePl: String, val nameEng: String) {
    WaitingForDirector("Oczekuje na dyrektora","Waiting for director"),
    WaitingForWilda("Oczekuje na Wildę","Waiting for Wilda"),
    WaitingForRector("Oczekuje na rektora", "Waiting for rector"),
    WaitingForWildaAgain("Oczekuje ponownie na Wildę","Waiting again for Wilda"),
    Declined("Odrzucony","Declined"),
    Accepted("Zaakceptowany","Accepted")
}