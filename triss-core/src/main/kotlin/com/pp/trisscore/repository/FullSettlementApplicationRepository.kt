package com.pp.trisscore.repository

import com.pp.trisscore.model.rows.FullSettlementApplication
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface FullSettlementApplicationRepository : ReactiveCrudRepository<FullSettlementApplication,Long> {
    fun findByIdAndCreatorId(id:Long,creatorId:Long) : Mono<FullSettlementApplication>
}
