package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.PrepaymentFee
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PrepaymentFeeRepository : ReactiveCrudRepository<PrepaymentFee,Long> {
}