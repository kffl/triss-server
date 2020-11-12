package com.pp.trisscore.service

import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.repository.TransportRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class TransportService(val transportRepository: TransportRepository) {

    fun save(transport: List<Transport>, id: Long): Mono<Transport> {
        var transportMono =  transportRepository.save(transport.get(0).copy(applicationID = id))
        var z = 1
        while(z<transport.size){
            val b=z
            transportMono = transportMono.flatMap { x -> transportRepository.save(transport[b].copy(applicationID = x.applicationID))}
            z=b+1
        }
        return transportMono
    }
}