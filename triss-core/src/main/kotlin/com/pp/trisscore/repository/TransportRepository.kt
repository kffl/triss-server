package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Transport
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository


@Repository
interface TransportRepository : ReactiveCrudRepository<Transport,Long> {}
