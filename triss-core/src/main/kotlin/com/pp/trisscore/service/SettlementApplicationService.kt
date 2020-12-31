package com.pp.trisscore.service

import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.SettlementApplication
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.repository.SettlementApplicationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class SettlementApplicationService(val settlementApplicationRepository: SettlementApplicationRepository) {
    fun createNewSettlement(application: Application): Mono<SettlementApplication> {
        val settlementApplication = SettlementApplication(null, application.id!!,StatusEnum.Edit.value, LocalDate.now())
        return settlementApplicationRepository.save(settlementApplication)
    }
}
