package com.pp.trisscore.service

import com.pp.trisscore.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EmployeeService(val employeeRepository: EmployeeRepository) {
    fun getAll() = employeeRepository.findAll()
}
