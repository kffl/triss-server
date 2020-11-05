package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Prepayment
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PrepaymentRepository : ReactiveCrudRepository<Prepayment,Long>