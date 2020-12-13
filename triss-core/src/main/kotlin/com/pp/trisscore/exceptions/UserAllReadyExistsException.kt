package com.pp.trisscore.exceptions

class UserAllReadyExistsException(message: String)
    : RuntimeException(message) {
    constructor(id: String, firstName: String, surname: String) : this("User $id $firstName $surname already exists.")
}
