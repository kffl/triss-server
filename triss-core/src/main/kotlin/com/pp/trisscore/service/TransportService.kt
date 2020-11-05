package com.pp.trisscore.service

import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.repository.TransportRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service


@Service
class TransportService(val transportRepository: TransportRepository) {

    fun createTransport(transport : Transport) = transportRepository.save(transport)

}