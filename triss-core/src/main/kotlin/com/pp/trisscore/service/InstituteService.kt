package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.repository.InstituteRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class InstituteService(val instituteRepository: InstituteRepository) {
    fun findInstituteByName(name:String) = instituteRepository.findByName(name).switchIfEmpty(Mono.error(ObjectNotFoundException("Institute")))
    fun findInstituteById(id:Long) = instituteRepository.findById(id).switchIfEmpty(Mono.error(ObjectNotFoundException("Institute")))
    fun getAllInstitute() = instituteRepository.findAll()
}
