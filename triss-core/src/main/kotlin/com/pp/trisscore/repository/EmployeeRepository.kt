package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Employee
import io.r2dbc.spi.ConnectionFactory
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface EmployeeRepository : ReactiveCrudRepository<Employee,Long> {
}
