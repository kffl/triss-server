package com.pp.trisscore.repository

import com.pp.trisscore.model.rows.ApplicationFull
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ApplicationFullRepository : ReactiveCrudRepository<ApplicationFull,Long> {
}