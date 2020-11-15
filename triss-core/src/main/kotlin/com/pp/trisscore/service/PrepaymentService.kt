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

        val conferenceFeeId = prepaymentFeeService.createPrepaymentFee(
                PrepaymentFee(id = null,
                        amount = advancePayments.conferenceFeeValue,
                        paymentType = advancePayments.conferenceFeePaymentTypeSelect)).map { x -> x.id }

        val depositFeeId = prepaymentFeeService.createPrepaymentFee(
                PrepaymentFee(id = null,
                        amount = advancePayments.depositFeeValue,
                        paymentType = advancePayments.depositFeePaymentTypeSelect)).map { x -> x.id }

        return Mono.zip(conferenceFeeId,depositFeeId).flatMap {
            data ->
            prepaymentRepository.save(
                    Prepayment(id = null,
                            conferenceFeeId = data.t1!!,
                            accommodationFeeId = data.t2!!))
        }
    }
}