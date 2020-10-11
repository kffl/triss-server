package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
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
            "AND country = :country "+
            "AND city = :city "+
            "AND abroadStartDate = :abroadStartDate "+
            "AND abroadEndDate = :abroadEndDate "+
            "AND status = :status " +
            "ORDER BY :orderBy :asc" +
            "LIMIT :pageSize")
    fun getAllByFilter(
                        pageSize: Int,
                        orderBy: String,
                        asc: String,
                        employeeId: Long?,
                        country: String?,
                        city: String?,
                        abroadStartDate: Date?,
                        abroadEndDate: Date?,
                        status: String?): Flux<ApplicationRow>

    @Query("SELECT COUNT(*)"+
            "FROM ApplicationRow "+
            "WHERE employeeId = :employeeId "+
            "AND country = :country "+
            "AND city = :city "+
            "AND abroadStartDate = :abroadStartDate "+
            "AND abroadEndDate = :abroadEndDate "+
            "AND status = :status ")
    fun getCountByFilter(employeeId: Long?,
                         country: String?,
                         city: String?,
                         abroadStartDate: Date?,
                         abroadEndDate: Date?,
                         status: String?): Mono<Int>
}