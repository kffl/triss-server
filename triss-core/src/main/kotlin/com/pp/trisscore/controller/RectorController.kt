package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.RectorNotFoundException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.RectorService
import com.pp.trisscore.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


@RestController
@CrossOrigin
@RequestMapping("/rector")
class RectorController(val rectorService: RectorService,
                       private val tokenService: TokenService) {
    val tokenBody = TokenData(3, "Jerzy", "Zbiałowierzy")


    @PostMapping("/application/get")
    fun getApplications(
//            token: JwtAuthenticationToken,
            @RequestBody pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return rectorService.getApplications(pageInfo, tokenBody)
    }

    @PostMapping("/application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>
//                         , token: JwtAuthenticationToken
    ): Mono<Long> {
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return rectorService.getCountByFilter(tokenBody, body)
    }

    @PostMapping("application/approve")
    fun approveApplication(@RequestBody body: ApplicationInfo
//                           , token: JwtAuthenticationToken
    ): Mono<Application> {
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return rectorService.approveApplication(tokenBody, body)
    }

    @PostMapping("application/getFull")
    fun getFullApplication(@RequestBody id: Long
//                           , token: JwtAuthenticationToken
    ) : Mono<ApplicationInfo>{
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return rectorService.getFullApplication(tokenBody, id)
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

    @ExceptionHandler(value = [RectorNotFoundException::class])
    fun catchRectorNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), ex.toString(), ex.message!!)
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails)
    }
}