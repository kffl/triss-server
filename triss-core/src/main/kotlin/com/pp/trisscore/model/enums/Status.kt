package com.pp.trisscore.model.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
enum class Status(val namePl: String, val nameEng: String) {
    WaitingForDirector("Oczekuje na dyrektora","Waiting for Director"),
    WaitingForWilda("Oczekuje na Wildę","Waiting for Wilda"),
    WaitingForRector("Oczekuje na rektora", "Waiting for Rector"),
    WaitingForWildaAgain("Oczekuje ponownie na Wildę","Waiting again for Wilda"),
    RejectedByDirector("Odrzucony przez Dyrektora","Rejected by Director"),
    RejectedByWilda("Odrzucony przez Wilde","Rejected by Wilda"),
    RejectedByRector("Odrzucony przez Rectora","Rejected by Rector"),
    Declined("Odrzucony","Declined"),
    Accepted("Zaakceptowany","Accepted")
}
