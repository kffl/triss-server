package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Employee
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : ReactiveCrudRepository<Employee,Long> {
}
