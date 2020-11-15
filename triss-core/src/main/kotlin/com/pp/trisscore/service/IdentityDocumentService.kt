package com.pp.trisscore.service

import com.pp.trisscore.model.classes.IdentityDocument
import com.pp.trisscore.repository.IdentityDocumentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class IdentityDocumentService(val identityDocumentRepository: IdentityDocumentRepository) {

    fun getIdentityDocument(id: IdentityDocument): Mono<IdentityDocument> {
        return identityDocumentRepository.findByEmployeeIDAndTypeAndNumber(employeeId = id.employeeID!!,
                type = id.type,
                number = id.number)
    }
}