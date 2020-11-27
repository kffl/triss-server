package com.pp.trisscore.model.enums

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
enum class Role {
    USER,
    WILDA,
    DIRECTOR,
    RECTOR
}
