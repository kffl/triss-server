package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Transport
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux


@Repository
interface TransportRepository : ReactiveCrudRepository<Transport,Long> {

    fun findAllByApplicationID(applicationID:Long) : Flux<Transport>


}
