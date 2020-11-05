package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.IdentityDocument
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface IdentityDocumentRepository : ReactiveCrudRepository<IdentityDocument,Long> {

    fun findByEmployeeIDAndTypeAndNumber(employeeId:Long,type:Int,number:String) : Mono<IdentityDocument>
}