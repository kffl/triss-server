package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.SettlementElement
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface SettlementElementRepository:ReactiveCrudRepository<SettlementElement,Long> {
    fun countAllBySettlementApplicationId(id:Long): Mono<Int>
    fun findAllBySettlementApplicationId(id:Long):Flux<SettlementElement>
}
