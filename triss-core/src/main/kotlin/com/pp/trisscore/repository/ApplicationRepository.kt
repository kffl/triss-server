package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Application
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Repository
interface ApplicationRepository : ReactiveCrudRepository<Application, Long> {

    fun getAllByEmployeeId(employeeId: Long): Flux<Application>

    fun getByIdAndInstituteId(id: Long, instituteId: Long): Mono<Application>
}
