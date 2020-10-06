package com.pp.trisscore.service

import com.pp.trisscore.model.classes.Request
import com.pp.trisscore.repository.RequestRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Service
class RequestService(val requestRepository: RequestRepository) {

    fun getByEmployeeId(employeeId: Long): Flux<Request> =
            requestRepository.getByEmployeeId(employeeId)
}