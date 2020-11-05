package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.FinancialSource
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface FinancialSourceRepository : ReactiveCrudRepository<FinancialSource,Long> {
}