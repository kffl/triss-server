package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.*
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.SettlementInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.SettlementApplication
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.model.rows.SettlementApplicationRow
import com.pp.trisscore.service.TokenService
import com.pp.trisscore.service.WildaService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*

@RestController
@CrossOrigin
@RequestMapping("/wilda")
class WildaController(
    private val wildaService: WildaService,
    private val tokenService: TokenService
) {


    @PostMapping("/application/get")
    fun getApplications(
        @RequestBody pageInfo: PageInfo<ApplicationRow>,
        token: JwtAuthenticationToken
    ): Mono<List<ApplicationRow>> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getApplications(pageInfo, tokenBody).collectList()
    }

    @PostMapping("/application/count")
    fun getCountByFilter(
        @RequestBody body: PageInfo<ApplicationRow>,
        token: JwtAuthenticationToken
    ): Mono<Long> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getCountByFilter(tokenBody, body)
    }

    @PostMapping("application/getFull")
    fun getFullApplication(
        @RequestBody id: Long,
        token: JwtAuthenticationToken
    ): Mono<ApplicationInfo> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getFullApplication(tokenBody, id)
    }

    @PostMapping("application/reject")
    fun rejectApplication(
        @RequestBody body: ApplicationInfo,
        token: JwtAuthenticationToken
    ): Mono<Application> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.rejectApplication(tokenBody, body)
    }


    @PostMapping("application/approve")
    fun approveApplication(
        @RequestBody body: ApplicationInfo,
        token: JwtAuthenticationToken
    ): Mono<Application> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.approveApplication(tokenBody, body)
    }

    @PostMapping("/settlement/get")
    fun getSettlementApplications(
        @RequestBody pageInfo: PageInfo<SettlementApplicationRow>,
        token: JwtAuthenticationToken
    ): Mono<List<SettlementApplicationRow>>  {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getSettlementApplications(pageInfo, tokenBody).collectList()
    }

    @PostMapping("settlement/getFull")
    fun getSettlementApplication(
        @RequestBody id: Long,
        token: JwtAuthenticationToken
    ): Mono<SettlementInfo> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getSettlementApplication(tokenBody, id)
    }

    @PostMapping("settlement/approve")
    fun approveSettlementApplication(
        @RequestBody settlementInfo: SettlementInfo,
        token: JwtAuthenticationToken
    ):Mono<SettlementApplication> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.changeSettlementApplicationStatus(tokenBody, settlementInfo,StatusEnum.Accepted.value)
    }

    @PostMapping("settlement/reject")
    fun rejectSettlementApplication(
        @RequestBody settlementInfo: SettlementInfo,
        token: JwtAuthenticationToken
    ):Mono<SettlementApplication> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.changeSettlementApplicationStatus(tokenBody, settlementInfo,StatusEnum.RejectedByWilda.value)
    }


    @ExceptionHandler(
        value = [InvalidRequestBodyException::class, RequestDataDiffersFromDatabaseDataException::class,
            UserAllReadyExistsException::class, WrongDateException::class]
    )
    fun catchInvalidRequestBodyException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }

    @ExceptionHandler(value = [ObjectNotFoundException::class, RectorNotFoundException::class])
    fun catchObjectNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
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
