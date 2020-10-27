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

    //TODO Long will be changed to filter object in future
    fun getAllByFilter(pageInfo: PageInfo<Long>): Flux<ApplicationRow> = applicationRowRepository.getAllByFilter(
                pageInfo.filter,
                pageInfo.orderBy,
                pageInfo.pageSize,
                pageInfo.pageNumber * pageInfo.pageSize)
    //TODO PageInfo will be changed to filter object in future
    fun getCountByFilter(pageInfo: PageInfo<Long>) = applicationRowRepository.getCountByFilter(pageInfo.filter)
}
