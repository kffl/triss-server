package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Institute
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface InstituteRepository:ReactiveCrudRepository<Institute,Long> {
    fun findByName(name:String): Mono<Institute>
    fun findAllByOrderByName(): Flux<Institute>
}
