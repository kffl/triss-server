package com.pp.trisscore.exceptions

import java.lang.RuntimeException

class ObjectNotFoundException(objectName:String) : RuntimeException(objectName + " not Found") {}