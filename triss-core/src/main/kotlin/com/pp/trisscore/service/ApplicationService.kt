package com.pp.trisscore.service

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.repository.ApplicationRepository
import com.pp.trisscore.repository.ApplicationRowRepositoryManager
import io.r2dbc.spi.ConnectionFactory
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Service
class ApplicationService(
        val applicationRowRepositoryManager: ApplicationRowRepositoryManager) {

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationRowRepositoryManager.getAllByFilter(pageInfo)
    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) = applicationRowRepositoryManager.getCountByFilter(pageInfo)
}
