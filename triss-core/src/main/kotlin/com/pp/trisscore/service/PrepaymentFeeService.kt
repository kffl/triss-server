package com.pp.trisscore.service

import com.pp.trisscore.model.classes.PrepaymentFee
import com.pp.trisscore.repository.PrepaymentFeeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PrepaymentFeeService(val prepaymentFeeRepository: PrepaymentFeeRepository) {


    fun createPrepaymentFee(fee:PrepaymentFee): Mono<PrepaymentFee>
    {
        return prepaymentFeeRepository.save(fee)
    }
}
