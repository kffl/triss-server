package com.pp.trisscore.model.enums

import com.fasterxml.jackson.annotation.JsonFormat

/**
 *
 * author: Marceli Jerzyński, Mateusz Tamborski
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 * Update date: 28.10.2020
 **/

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
enum class PaymentType(val namePl: String, val nameEng: String) {
    Cash("Gotówka", "Cash"),
    Blik("Blik","Blik"),
    Card("Karta","Card"),
    Transfer("Przelew","Transfer")
}
