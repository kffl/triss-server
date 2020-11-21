package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.service.ApplicationService
import com.pp.trisscore.service.EmployeeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/employee")
class EmployeeController(val employeeService: EmployeeService,
                         val applicationService: ApplicationService) {
    @PostMapping("/get")
    fun getEmployee(token: JwtAuthenticationToken): Mono<Employee> {
        return employeeService.getEmployee(token)
    }

    @PostMapping("/update")
    fun updateEmployee(token: JwtAuthenticationToken, @RequestBody employee: Employee): Mono<Employee> {
        return employeeService.saveEmployee(token,employee)
    }


    @ExceptionHandler(value = [InvalidRequestBodyException::class])
    fun catchInvalidRequestBodyException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }

    @ExceptionHandler(value = [ObjectNotFoundException::class])
    fun catchObjectNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.GONE).body(errorDetails)
    }

    @ExceptionHandler(value = [EmployeeNotFoundException::class])
    fun catchEmployeeNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails)
    }






}