package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.SettlementElement
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SettlementElementRepository:ReactiveCrudRepository<SettlementElement,Long> {}
