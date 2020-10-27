package com.pp.trisscore.repository

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.rows.ApplicationRow
import io.r2dbc.spi.ConnectionFactory
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.core.select
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Repository
class ApplicationRowRepositoryManager(val applicationRowRepository: ApplicationRowRepository,
                                      connectionFactory: ConnectionFactory) {


    val template =R2dbcEntityTemplate( DatabaseClient.create(connectionFactory))

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        return template.select(Query.query(getCriteria(pageInfo)).sort(pageInfo.getSort()).limit(pageInfo.pageSize).offset(pageInfo.pageNumber),ApplicationRow::class.java)
    }

    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>): Mono<Long> {
        return template.count(Query.query(getCriteria(pageInfo)),ApplicationRow::class.java)
    }

    fun getCriteria(pageInfo: PageInfo<ApplicationRow>): Criteria {
        val filter = pageInfo.filter
        var criteria = Criteria.where("id").isNotNull
        if (filter.id != null)
            criteria = criteria.and("id").`is`(filter.id)
        if (filter.employeeId != null)
            criteria = criteria.and("employeeId").`is`(filter.employeeId)
        if (filter.city != null)
            criteria = criteria.and("city").like("%" + filter.city + "%").ignoreCase(true)
        if (filter.country != null)
            criteria = criteria.and("country").like("%" + filter.country + "%").ignoreCase(true)
        if (filter.status != null)
            criteria = criteria.and("status").like("%" + filter.status + "%").ignoreCase(true)
        //TODO niepoprawne porównywanie dat!!!
        if (filter.abroadEndDate != null)
            criteria = criteria.and("abroadEndDate").lessThanOrEquals(java.util.Date(filter.abroadEndDate.time).toInstant())
        if (filter.abroadStartDate != null)
            criteria = criteria.and("abroadStartDate").greaterThanOrEquals(java.util.Date(filter.abroadStartDate.time).toInstant())

        return criteria
    }
}