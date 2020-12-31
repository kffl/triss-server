package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.*
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.SettlementApplication
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.TokenService
import com.pp.trisscore.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


@RestController
@CrossOrigin
@RequestMapping("/user")
class UserController(private val userService: UserService,
                     private val tokenService: TokenService) {


    @PostMapping("application/get")
    fun getMyApplications(
                          @RequestBody body: PageInfo<ApplicationRow>,
                          token: JwtAuthenticationToken
    ): Mono<List<ApplicationRow>> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getMyApplications(tokenData, body.copy(filter = body.filter.copy(employeeId = tokenData.employeeId))).collectList()
    }

    @PostMapping("application/count")
    fun getCountByFilter(
                         @RequestBody body: PageInfo<ApplicationRow>,
                         token: JwtAuthenticationToken
    ): Mono<Long> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getCountByFilter(tokenData, body.copy(filter = body.filter.copy(employeeId = tokenData.employeeId)))
    }

    @PostMapping("application/getFull")
    fun getFullApplication(
                           @RequestBody id: Long,
                           token: JwtAuthenticationToken
    ): Mono<ApplicationInfo> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getMyFullApplication(tokenData, id)
    }

    @PostMapping("application/create")
    fun createApplication(
                          @RequestBody body: ApplicationInfo,
                          token: JwtAuthenticationToken
    ): Mono<Transport>{
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.createApplication(tokenData, body)
    }

    @PostMapping("settlement/create")
    fun createSettlementApplication(@RequestBody body:Long,token: JwtAuthenticationToken) :Mono<SettlementApplication>{
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.createSettlementApplication(tokenData,body)
    }

    @ExceptionHandler(value = [InvalidRequestBodyException::class, RequestDataDiffersFromDatabaseDataException::class,
        UserAllReadyExistsException::class, WrongDateException::class])
    fun catchInvalidRequestBodyException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }

    @ExceptionHandler(value = [ObjectNotFoundException::class,UserNotFoundException::class])
    fun catchNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.GONE).body(errorDetails)
    }

    @ExceptionHandler(value = [UnauthorizedException::class])
    fun catchUnauthorizedException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails)
    }

    @ExceptionHandler(value = [Exception::class])
    fun catchAllExceptions(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails)
    }

}
