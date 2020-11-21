package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.enums.Role
import io.r2dbc.spi.ConnectionFactory
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
interface EmployeeRepository : ReactiveCrudRepository<Employee,Long> {
    fun findByIdAndFirstNameAndSurname(id:Long,firstName:String,surname:String) : Mono<Employee>
}
