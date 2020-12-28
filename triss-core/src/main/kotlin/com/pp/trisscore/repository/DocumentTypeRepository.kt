package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.DocumentType
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DocumentTypeRepository : ReactiveCrudRepository<DocumentType, Long> {
}
