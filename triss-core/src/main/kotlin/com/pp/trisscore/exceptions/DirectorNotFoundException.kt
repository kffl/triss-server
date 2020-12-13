package com.pp.trisscore.exceptions

import java.lang.RuntimeException

class DirectorNotFoundException(message:String)
    : RuntimeException(message){
    constructor(id : String,firstName:String,surname:String):this("Director $id $firstName $surname not found.")
}
