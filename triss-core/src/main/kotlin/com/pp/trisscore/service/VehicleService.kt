package com.pp.trisscore.service

import com.pp.trisscore.repository.VehicleRepository
import org.springframework.stereotype.Service

@Service
class VehicleService(val vehicleRepository: VehicleRepository) {
    fun findById(id: Long) = vehicleRepository.findById(id)
    fun getVehicles() = vehicleRepository.findAll()
}
