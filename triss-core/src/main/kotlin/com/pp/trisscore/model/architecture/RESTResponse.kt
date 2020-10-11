package com.pp.trisscore.model.architecture

import com.pp.trisscore.model.rows.ApplicationRow
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class RESTResponse<T>(
        val data: Flux<ApplicationRow>?,
        val count: Mono<Int>?,
        val isSuccess: Boolean
)
