package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.repository.TransportRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class TransportService(val transportRepository: TransportRepository,
                       val vehicleService: VehicleService) {

    fun saveAll(transport: List<Transport>, id: Long): Mono<Transport> {
        var transportMono = transportRepository.save(transport[0].copy(applicationID = id))
        var z = 1
        while (z < transport.size) {
            val b = z
            transportMono = transportMono.flatMap { x -> save(transport[b].copy(applicationID = x.applicationID)) }
            z = b + 1
        }
        return transportMono
    }

    private fun save(transport: Transport): Mono<Transport>? {
        return vehicleService.findById(transport.vehicleSelect)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("VehicleType")))
                .flatMap { transportRepository.save(transport) }
    }

    fun findAllByApplicationID(applicationId: Long) = transportRepository.findAllByApplicationID(applicationId)
}
