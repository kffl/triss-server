package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EmployeeController(val employeeService: EmployeeService) {
    @GetMapping("/employee")
    fun getAll() = employeeService.findAll()
}
