package com.pp.trisscore.exceptions

import java.lang.RuntimeException

class RequestDataDiffersFromDatabaseDataException(name:String) :RuntimeException("$name in DB differs from the request's $name")
