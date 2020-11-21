package com.pp.trisscore.service

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.repository.EmployeeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {

    fun findEmployee(tokenData: TokenData) =
        employeeRepository.findById(tokenData.employeeId)
                .switchIfEmpty(Mono.error(EmployeeNotFoundException("Employee not Found")))




}