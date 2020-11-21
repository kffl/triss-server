package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.service.EmployeeService
import com.pp.trisscore.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/employee")
class EmployeeController(val employeeService: EmployeeService,
                         val tokenService: TokenService) {
    @PostMapping("/get")
    fun getEmployee(@AuthenticationPrincipal token: JwtAuthenticationToken): Mono<Employee> {
        val data = tokenService.getEmployeeDataFromToken(token)
        return employeeService.findEmployee(data)
    }

    @ExceptionHandler(value = [EmployeeNotFoundException::class])
    fun catchEmployeeNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }
}