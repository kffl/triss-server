package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.SettlementApplication
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SettlementApplicationRepository : ReactiveCrudRepository<SettlementApplication,Long> {}
