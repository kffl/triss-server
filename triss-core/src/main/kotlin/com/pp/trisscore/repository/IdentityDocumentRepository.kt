package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.IdentityDocument
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface IdentityDocumentRepository : ReactiveCrudRepository<IdentityDocument,Long> {

    fun findByEmployeeIdAndTypeAndNumber(employeeId:Long, type:Int, number:String) : Mono<IdentityDocument>
}
