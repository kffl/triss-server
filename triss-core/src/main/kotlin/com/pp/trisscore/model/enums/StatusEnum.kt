package com.pp.trisscore.model.enums

enum class StatusEnum(val value:Long) {
    WaitingForDirector(1),
    WaitingForWilda(2),
    WaitingForRector(3),
    RejectedByDirector(4),
    RejectedByWilda(5),
    RejectedByRector(6),
    Accepted(7)
}
