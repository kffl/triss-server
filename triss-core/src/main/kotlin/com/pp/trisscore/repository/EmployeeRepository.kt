package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.enums.Role
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface EmployeeRepository : ReactiveCrudRepository<Employee, Long> {
    fun findByEmployeeId(employeeId: Long): Mono<Employee>
    fun findByEmployeeIdAndEmployeeType(employeeId: Long, employeeType: Role): Mono<Employee>
}
