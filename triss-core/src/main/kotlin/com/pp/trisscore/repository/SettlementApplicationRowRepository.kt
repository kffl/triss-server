package com.pp.trisscore.repository

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.model.rows.SettlementApplicationRow
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class SettlementApplicationRowRepository(databaseClient: DatabaseClient) {

    val template = R2dbcEntityTemplate(databaseClient)

    fun getAllByFilter(pageInfo: PageInfo<SettlementApplicationRow>): Flux<SettlementApplicationRow> {
        return template.select(Query.query(getCriteria(pageInfo))
            .sort(pageInfo.getSort())
            .limit(pageInfo.pageSize)
            .offset(pageInfo.pageNumber * pageInfo.pageSize),
            SettlementApplicationRow::class.java)
    }

    fun getCountByFilter(pageInfo: PageInfo<SettlementApplicationRow>): Mono<Long> {
        return template.count(Query.query(getCriteria(pageInfo)), SettlementApplicationRow::class.java)
    }

    fun getCriteria(pageInfo: PageInfo<SettlementApplicationRow>): Criteria {
        val filter = pageInfo.filter
        var criteria = Criteria.where("id").isNotNull
        if (filter.id != null)
            criteria = criteria.and("id").`is`(filter.id)
        if (filter.applicationId != null)
            criteria = criteria.and("applicationId").`is`(filter.applicationId)
        if(filter.firstName!=null)
            criteria = criteria.and("firstName").like("%" + filter.firstName + "%").ignoreCase(true)
        if(filter.surname!=null)
            criteria = criteria.and("surname").like("%" + filter.surname + "%").ignoreCase(true)
        if (filter.employeeId != null)
            criteria = criteria.and("employeeId").`is`(filter.employeeId)
        if (filter.city != null)
            criteria = criteria.and("city").like("%" + filter.city + "%").ignoreCase(true)
        if (filter.country != null)
            criteria = criteria.and("country").like("%" + filter.country + "%").ignoreCase(true)
        if (filter.status!=null)
            criteria = criteria.and("status").like(filter.status)
        if (filter.statusPl != null)
            criteria = criteria.and("statusPl").like(filter.statusPl).ignoreCase(true)
        if (filter.statusEng != null)
            criteria = criteria.and("statusEng").like(filter.statusEng).ignoreCase(true)
        if(filter.instituteId!=null)
            criteria = criteria.and("instituteId").`is`(filter.instituteId)
        if(filter.instituteName!=null)
            criteria = criteria.and("instituteName").like("%" + filter.instituteName + "%").ignoreCase(true)
        if (filter.abroadEndDate != null)
            criteria = criteria.and("abroadEndDate").lessThanOrEquals(filter.abroadEndDate.toLocalDate().atStartOfDay())
        if (filter.abroadStartDate != null)
            criteria = criteria.and("abroadStartDate").greaterThanOrEquals(filter.abroadStartDate.toLocalDate().atStartOfDay())
        return criteria
    }
}
