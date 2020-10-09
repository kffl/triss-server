package com.pp.trisscore.service

import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.repository.ApplicationRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Service
class ApplicationService(val applicationRepository: ApplicationRepository) {

    fun getAllByEmployeeID(employeeID: Long): Flux<Application> =
            applicationRepository.getAllByEmployeeId(employeeID)
}
