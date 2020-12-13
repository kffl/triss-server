package com.pp.trisscore.exceptions

class RectorNotFoundException(message: String)
    : RuntimeException(message) {
    constructor(id: String, firstName: String, surname: String) : this("Rector $id $firstName $surname not found.")
}
