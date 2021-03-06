package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.UserAllReadyExistsException
import com.pp.trisscore.exceptions.UserNotFoundException
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.service.EmployeeService
import com.pp.trisscore.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/employee")
class EmployeeController(private val employeeService: EmployeeService,
                         private val tokenService: TokenService) {

    @PostMapping("/get")
    fun getEmployee(
                    token: JwtAuthenticationToken
    ): Mono<Employee> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return employeeService.findEmployeeOrCreateNewOne(tokenData)
    }

    @PostMapping("/update")
    fun updateEmployee(
                       @RequestBody employee: Employee,
                       token: JwtAuthenticationToken
    ): Mono<Employee> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return employeeService.updateEmployee(tokenData,employee)
    }

    @PostMapping("/create")
    fun createEmployee(
                       @RequestBody employee: Employee,
                       token: JwtAuthenticationToken
    ): Mono<Employee> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return employeeService.newEmployee(tokenData,employee)
    }

    @ExceptionHandler(value = [InvalidRequestBodyException::class, UserAllReadyExistsException::class])
    fun catchInvalidRequestBodyException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }

    @ExceptionHandler(value = [UserNotFoundException::class,ObjectNotFoundException::class])
    fun catchNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.GONE).body(errorDetails)
    }
    @ExceptionHandler(value = [Exception::class])
    fun catchAllExceptions(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails)
    }
}
