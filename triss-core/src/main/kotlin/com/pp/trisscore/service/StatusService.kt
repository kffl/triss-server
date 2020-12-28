package com.pp.trisscore.service

import com.pp.trisscore.repository.StatusRepository
import org.springframework.stereotype.Service

@Service
class StatusService(val statusRepository: StatusRepository) {
    fun getStatuses() = statusRepository.findAll()
}
