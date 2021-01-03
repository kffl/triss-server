package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.*
import com.pp.trisscore.model.architecture.*
import com.pp.trisscore.model.classes.SettlementApplication
import com.pp.trisscore.model.classes.SettlementElement
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.model.rows.SettlementApplicationRow
import com.pp.trisscore.service.TokenService
import com.pp.trisscore.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
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
    fun getCountApplicationsByFilter(
                         @RequestBody body: PageInfo<ApplicationRow>,
                         token: JwtAuthenticationToken
    ): Mono<Long> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getCountApplicationsByFilter(tokenData, body.copy(filter = body.filter.copy(employeeId = tokenData.employeeId)))
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
    @PostMapping("/settlement/get")
    fun getSettlementApplications(
        @RequestBody pageInfo: PageInfo<SettlementApplicationRow>,
        token: JwtAuthenticationToken
    ): Mono<List<SettlementApplicationRow>> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return userService.getSettlementApplications(pageInfo, tokenBody).collectList()
    }

    @PostMapping("/settlement/count")
    fun getCountSettlementApplicationsByFilter(
        @RequestBody body: PageInfo<SettlementApplicationRow>,
        token: JwtAuthenticationToken
    ): Mono<Long> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return userService.getCountSettlementApplicationsByFilter(tokenBody, body)
    }

    @PostMapping("settlement/getFull")
    fun getSettlementApplication(@RequestBody body:Long,token: JwtAuthenticationToken) :Mono<SettlementInfo>{
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getSettlementApplication(tokenData,body)
    }
    @PostMapping("settlement/send")
    fun sendToWildaSettlementApplication(@RequestBody body:Long,token: JwtAuthenticationToken) :Mono<SettlementApplication>{
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.sendToWildaSettlementApplication(tokenData,body)
    }
    @PostMapping("settlement/element/add")
    fun addSettlementElement(@RequestBody body:SettlementElement,token: JwtAuthenticationToken) :Mono<SettlementApplication>{
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.addSettlementElement(tokenData,body)
    }
    @PostMapping("settlement/element/delete")
    fun deleteSettlementElement(@RequestBody body:SettlementElement,token: JwtAuthenticationToken) : Mono<Void> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.deleteSettlementElement(tokenData,body)
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
