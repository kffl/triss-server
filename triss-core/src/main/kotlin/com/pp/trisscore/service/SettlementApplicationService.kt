package com.pp.trisscore.service

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.classes.SettlementApplication
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.repository.SettlementApplicationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class SettlementApplicationService(val settlementApplicationRepository: SettlementApplicationRepository) {
    fun createNewSettlement(application: Application, user: Employee): Mono<SettlementApplication> {
        val settlementApplication = SettlementApplication(null, application.id!!, user.employeeId!!,
            StatusEnum.Edit.value, LocalDate.now(), null)
        return settlementApplicationRepository.findAllByApplicationId(application.id).collectList().flatMap { x ->
            if (x.isNotEmpty())
                Mono.error(InvalidRequestBodyException("Settlement all ready exists"))
            else
                settlementApplicationRepository.save(settlementApplication)
        }
    }

    fun findById(settlementApplicationId: Long) = settlementApplicationRepository.findById(settlementApplicationId)
    fun saveSettlement(settlement: SettlementApplication): Mono<SettlementApplication> =
        settlementApplicationRepository.save(settlement)
}
