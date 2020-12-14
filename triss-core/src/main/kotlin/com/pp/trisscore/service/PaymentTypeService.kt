package com.pp.trisscore.service

import com.pp.trisscore.repository.PaymentTypeRepository
import org.springframework.stereotype.Service

@Service
class PaymentTypeService(private val paymentTypeRepository: PaymentTypeRepository) {
    fun getPaymentTypes() = paymentTypeRepository.findAll()
    fun findById(id: Long) = paymentTypeRepository.findById(id)
}
