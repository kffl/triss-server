package com.pp.trisscore.service

import com.pp.trisscore.repository.EnumRepository
import org.springframework.stereotype.Service

@Service
class EnumService(val enumRepository: EnumRepository) {
    fun getDocumentTypes() = enumRepository.getDocumentTypes()
    fun getPaymentTypes() = enumRepository.getPaymentTypes()
    fun getStatuses() = enumRepository.getStatuses()
    fun getVehicles() = enumRepository.getVehicles()
}
