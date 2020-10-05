package com.pp.trisscore.model.classes

import com.pp.trisscore.model.enums.DocumentType

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 03.10.2020
 **/
data class IdentityDocument (
        val Type: DocumentType,
        val Name: String,
        val Number: String
)