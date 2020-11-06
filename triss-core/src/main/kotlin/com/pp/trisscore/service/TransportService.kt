package com.pp.trisscore.service

import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.repository.TransportRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class TransportService(val transportRepository: TransportRepository) {

    fun saveAll(transportList:List<Transport>,applicationId:Long): Flux<Transport>
    {
        return transportRepository.saveAll(transportList)
    }

    fun save(transport: List<Transport>, id: Long): Mono<Transport> {
        val transportMono =  transportRepository.save(transport.get(0).copy(applicationID = id))
        val z = 1
        if(z< transport.size)
            return save(transport,id,z,transportMono)
        return transportMono
    }
    fun save(transport: List<Transport>, id:Long, z:Int,transportMono: Mono<Transport>) :Mono<Transport>
    {
        val transportM =  transportMono.flatMap { x -> transportRepository.save(transport.get(z).copy(applicationID = x.applicationID))}
        val y = z+1
        if(y< transport.size)
            return save(transport,id,y,transportM)
        return transportM
    }

}