package com.pp.trisscore.service

import com.pp.trisscore.repository.DocumentTypeRepository
import org.springframework.stereotype.Service

@Service
class DocumentTypeService(val documentTypeRepository: DocumentTypeRepository) {
    fun getDocumentTypes() = documentTypeRepository.findAll()
    fun findById(id: Long) = documentTypeRepository.findById(id)


}
