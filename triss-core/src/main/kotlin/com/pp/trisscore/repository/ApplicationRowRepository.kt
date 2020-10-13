package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.data.domain.Pageable
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Repository
interface ApplicationRowRepository : ReactiveCrudRepository<ApplicationRow, Long> {

    @Query("SELECT * "+
            "FROM ApplicationRow "+
            "WHERE employeeId = :employeeId "+
            "ORDER BY :orderBy " +
            "LIMIT :pageSize " +
            "OFFSET :pageNumber")
    fun getAllByFilter(
                employeeId: Long,
                orderBy: String,
                pageSize: Int,
                pageNumber: Int): Flux<ApplicationRow>

    @Query("SELECT COUNT(*) "+
            "FROM ApplicationRow "+
            "WHERE employeeId = :employeeId ")
    fun getCountByFilter(employeeId: Long): Mono<Int>
}