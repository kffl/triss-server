package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.*
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.DirectorService
import com.pp.trisscore.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.*


@RestController
@CrossOrigin
@RequestMapping("/director")
class DirectorController(private val directorService: DirectorService,
                         private val tokenService: TokenService) {

    @PostMapping("application/get")
    fun getApplications(
            @RequestBody pageInfo: PageInfo<ApplicationRow>,
            token: JwtAuthenticationToken
    ): Mono<List<ApplicationRow>> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.getApplications(pageInfo, tokenBody).collectList()
    }

    @PostMapping("application/count")
    fun getCountByFilter(
            @RequestBody body: PageInfo<ApplicationRow>,
            token: JwtAuthenticationToken
    ): Mono<Long> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.getCountByFilter(tokenBody, body)
    }

    @PostMapping("application/approve")
    fun approveApplication(
            @RequestBody body: ApplicationInfo,
            token: JwtAuthenticationToken
    ): Mono<Application> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.approveApplication(tokenBody, body)
    }


    @PostMapping("application/reject")
    fun rejectApplication(
            @RequestBody body: ApplicationInfo,
            token: JwtAuthenticationToken
    ): Mono<Application> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.rejectApplication(tokenBody, body)
    }


    @PostMapping("application/getFull")
    fun getFullApplication(
            @RequestBody id: Long,
            token: JwtAuthenticationToken
    ): Mono<ApplicationInfo> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.getFullApplication(tokenBody, id)
    }

    @ExceptionHandler(value = [InvalidRequestBodyException::class, RequestDataDiffersFromDatabaseDataException::class,
        UserAllReadyExistsException::class, WrongDateException::class])
    fun catchInvalidRequestBodyException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }

    @ExceptionHandler(value = [ObjectNotFoundException::class])
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

