package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.PrepaymentFee
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface PrepaymentFeeRepository : ReactiveCrudRepository<PrepaymentFee,Long> {
}