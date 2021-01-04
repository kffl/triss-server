package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.SettlementInfo
import com.pp.trisscore.repository.FullSettlementApplicationRepository
import com.pp.trisscore.repository.FullSettlementElementRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class FullSettlementService(
    private val fullSettlementApplicationRepository: FullSettlementApplicationRepository,
    private val settlementElementService: SettlementElementService
) {
    fun findSettlementApplicationByIdAndEmployeeId(id:Long,employeeId:Long) = fullSettlementApplicationRepository.findByIdAndCreatorId(id,employeeId)
    fun findSettlementApplicationById(id:Long) = fullSettlementApplicationRepository.findById(id)
    fun findSettlementInfoByIdAndEmployeeId(id:Long,employeeId:Long): Mono<SettlementInfo> {
        val full = findSettlementApplicationByIdAndEmployeeId(id, employeeId)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("Settlement Application")))
        val list = settlementElementService.findAllBySettlementId(id)
        return Mono.zip(full, list.collectList()).map { x -> SettlementInfo(x.t1, x.t2) }
    }
    fun findSettlementInfoById(id:Long): Mono<SettlementInfo> {
        val full = findSettlementApplicationById(id)
            .switchIfEmpty(Mono.error(ObjectNotFoundException("Settlement Application")))
        val list = settlementElementService.findAllBySettlementId(id)
        return Mono.zip(full, list.collectList()).map { x -> SettlementInfo(x.t1, x.t2) }
    }
}
