package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Request
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Repository
interface RequestRepository : ReactiveCrudRepository<Request, Long> {

    @Query("SELECT * FROM Requests WHERE employeeId = :employeeId")
    fun getByEmployeeId(employeeId: Long): Flux<Request>
}