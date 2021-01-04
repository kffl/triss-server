package com.pp.trisscore.exceptions

class UserNotFoundException(message: String) : RuntimeException(message) {
    constructor(id: Long, firstName: String, surname: String) : this("User $id $firstName $surname not found.")
}
