package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.SettlementApplication
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface SettlementApplicationRepository : ReactiveCrudRepository<SettlementApplication,Long> {
    fun findAllByApplicationId(id: Long): Flux<SettlementApplication>
}
