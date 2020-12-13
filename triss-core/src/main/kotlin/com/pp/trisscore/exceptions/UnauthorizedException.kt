package com.pp.trisscore.exceptions

class UnauthorizedException(message: String)
    : RuntimeException(message) {
    constructor(id: String, firstName: String, surname: String) : this("User $id $firstName $surname don't have access to this.")
}
