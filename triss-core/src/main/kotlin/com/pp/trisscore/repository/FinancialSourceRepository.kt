package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.FinancialSource
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface FinancialSourceRepository : ReactiveCrudRepository<FinancialSource,Long> {
}