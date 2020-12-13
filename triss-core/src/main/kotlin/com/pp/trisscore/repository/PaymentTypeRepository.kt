package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.PaymentType
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentTypeRepository : ReactiveCrudRepository<PaymentType, Long> {
}
