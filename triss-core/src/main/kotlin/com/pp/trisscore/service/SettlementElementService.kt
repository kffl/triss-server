package com.pp.trisscore.service

import com.pp.trisscore.model.classes.SettlementElement
import com.pp.trisscore.model.rows.FullSettlementElement
import com.pp.trisscore.repository.FullSettlementElementRepository
import com.pp.trisscore.repository.SettlementElementRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SettlementElementService(
    val settlementElementRepository: SettlementElementRepository,
    val fullSettlementElementRepository: FullSettlementElementRepository
) {
    fun addNewElement(element: SettlementElement) = settlementElementRepository.save(element)

    fun findBy(saId: Long, seId: Long, creatorId: Long) : Mono<FullSettlementElement> =
        fullSettlementElementRepository.findBySettlementApplicationIdAndCreatorIdAndSettlementElementId(
            saId,
            creatorId,
            seId
        )

    fun deleteById(id:Long) = settlementElementRepository.deleteById(id)
    fun findAllBySettlementId(id: Long) = settlementElementRepository.findAllBySettlementApplicationId(id)

}
