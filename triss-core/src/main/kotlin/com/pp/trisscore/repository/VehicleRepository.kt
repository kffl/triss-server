package com.pp.trisscore.repository
import com.pp.trisscore.model.classes.Vehicle
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VehicleRepository:ReactiveCrudRepository<Vehicle,Long> {}
