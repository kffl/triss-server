package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Status
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface StatusRepository : ReactiveCrudRepository<Status,Long>
