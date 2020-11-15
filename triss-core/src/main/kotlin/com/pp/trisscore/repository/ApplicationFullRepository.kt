package com.pp.trisscore.repository

import com.pp.trisscore.model.rows.ApplicationFull
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface ApplicationFullRepository : ReactiveCrudRepository<ApplicationFull,Long> {}