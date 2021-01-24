package com.pp.trisscore.model.enums

enum class StatusEnum(val value:Long) {
    Edit(0),
    WaitingForDirector(1),
    RejectedByDirector(4),
    WaitingForWilda(2),
    RejectedByWilda(5),
    WaitingForRector(3),
    RejectedByRector(6),
    Accepted(7)
}
