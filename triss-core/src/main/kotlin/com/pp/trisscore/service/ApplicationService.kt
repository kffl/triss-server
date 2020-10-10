package com.pp.trisscore.service

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

    fun getAllByFilter(applicationRow: ApplicationRow): Flux<ApplicationRow> =
            applicationRowRepository.getAllByFilter(applicationRow.employeeId,
                                                        applicationRow.country,
                                                        applicationRow.city,
                                                        applicationRow.abroadStartDate,
                                                        applicationRow.abroadEndDate,
                                                        applicationRow.status)

    fun getCountByFilter(applicationRow: ApplicationRow) =
            applicationRowRepository.getCountByFilter(applicationRow.employeeId,
                    applicationRow.country,
                    applicationRow.city,
                    applicationRow.abroadStartDate,
                    applicationRow.abroadEndDate,
                    applicationRow.status)
}
