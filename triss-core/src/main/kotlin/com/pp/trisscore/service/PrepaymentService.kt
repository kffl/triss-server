package com.pp.trisscore.service

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.classes.Prepayment
import com.pp.trisscore.model.classes.PrepaymentFee
import com.pp.trisscore.repository.PrepaymentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PrepaymentService(val prepaymentRepository: PrepaymentRepository,
                        val prepaymentFeeService: PrepaymentFeeService) {
    fun createPrepayment(advancePayments: AdvancePaymentsInfo): Mono<Prepayment> {

        return prepaymentFeeService.createPrepaymentFee(
                PrepaymentFee(id = null,
                        amount = advancePayments.conferenceFeeValue,
                        paymentType = advancePayments.conferenceFeePaymentTypeSelect)).map { x -> x.id }

        val depositFeeId = prepaymentFeeService.createPrepaymentFee(
                PrepaymentFee(id = null,
                        amount = advancePayments.depositValue,
                        paymentType = advancePayments.depositPaymentTypeSelect)).map { x -> x.id }

        return prepaymentRepository.save(
                Prepayment(id = null,
                        conferenceFeeId = conferenceFeeId.block()!!,
                        accommodationFeeId = depositFeeId.block()!!))
    }
}