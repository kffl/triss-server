package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.repository.ApplicationFullRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class ApplicationFullService(val applicationFullRepository: ApplicationFullRepository,
                             val transportService: TransportService) {

    fun getFullApplication(id: Long): Mono<ApplicationInfo> {
        val applicationFull = applicationFullRepository.findById(id)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Application")))
        val transportList = transportService.findAllByApplicationID(id)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Transport")))
        return Mono.zip(applicationFull, transportList.collectList()).flatMap { x ->
            Mono.just(x.t1.getApplicationInfo(x.t2))
        }
    }

    fun getFullUserApplication(id: Long, tokenData: TokenData): Mono<ApplicationInfo> {
        val applicationFull = applicationFullRepository.findByIdAndEmployeeId(id, tokenData.employeeId)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Application")))
        val transportList = transportService.findAllByApplicationID(id)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Transport")))
        return Mono.zip(applicationFull, transportList.collectList()).flatMap { x ->
            Mono.just(x.t1.getApplicationInfo(x.t2))
        }
    }
}