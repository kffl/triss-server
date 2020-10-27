package com.pp.trisscore.service

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.repository.EmployeeRepository
import io.r2dbc.spi.ConnectionFactory
import org.springframework.stereotype.Service
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.r2dbc.repository.Query
import reactor.core.publisher.Flux


@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun findAll()= employeeRepository.findAll()


}

