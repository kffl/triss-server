package com.pp.trisscore.service

import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.repository.FinancialSourceRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class FinancialSourceService(val financialSourceRepository: FinancialSourceRepository) {



    fun createFinancialSource(financialSource: FinancialSource): Mono<FinancialSource>
    {
        return financialSourceRepository.save(financialSource)
    }

}