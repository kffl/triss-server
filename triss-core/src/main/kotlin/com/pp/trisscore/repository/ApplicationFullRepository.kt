package com.pp.trisscore.repository

import com.pp.trisscore.model.rows.ApplicationFull
import org.springframework.data.annotation.Id
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface ApplicationFullRepository : ReactiveCrudRepository<ApplicationFull,Long> {
    fun findByIdAndEmployeeId(id:Long,employeeId:Long): Mono<ApplicationFull>
    fun findByIdAndInstituteId(id: Long,instituteId: Long):Mono<ApplicationFull>
}