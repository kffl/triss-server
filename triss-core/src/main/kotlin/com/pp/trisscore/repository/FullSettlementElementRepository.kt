package com.pp.trisscore.repository

import com.pp.trisscore.model.rows.FullSettlementElement
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface FullSettlementElementRepository : ReactiveCrudRepository<FullSettlementElement, Long> {

    fun findBySettlementApplicationIdAndCreatorIdAndSettlementElementId(
        settlementApplicationId: Long,
        creatorId: Long,
        settlementElementId: Long
    ): Mono<FullSettlementElement>
}
