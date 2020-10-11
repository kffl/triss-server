package com.pp.trisscore.service

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.repository.ApplicationRepository
import com.pp.trisscore.repository.ApplicationRowRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Service
class ApplicationService(
        val applicationRepository: ApplicationRepository,
        val applicationRowRepository: ApplicationRowRepository) {
    val ASC: String = "ASC"
    val DESC: String = "DESC"

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        var asc = DESC
        if (pageInfo.asc) {
            asc = ASC
        }
        return applicationRowRepository.getAllByFilter(
                pageInfo.pageSize,
                pageInfo.orderBy,
                asc,
                pageInfo.filter.employeeId,
                pageInfo.filter.country,
                pageInfo.filter.city,
                pageInfo.filter.abroadStartDate,
                pageInfo.filter.abroadEndDate,
                pageInfo.filter.status)
    }

    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) =
            applicationRowRepository.getCountByFilter(pageInfo.filter.employeeId,
                    pageInfo.filter.country,
                    pageInfo.filter.city,
                    pageInfo.filter.abroadStartDate,
                    pageInfo.filter.abroadEndDate,
                    pageInfo.filter.status)
}
