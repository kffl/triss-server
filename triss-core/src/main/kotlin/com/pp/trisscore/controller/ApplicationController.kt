package com.pp.trisscore.controller

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.WrongDateException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.ErrorsDetails
import com.pp.trisscore.model.architecture.GetFullApplicationRequestBody
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationFull
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.ApplicationFullService
import com.pp.trisscore.service.ApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@RestController
@CrossOrigin
@RequestMapping("/application")
class ApplicationController(val applicationService: ApplicationService,
                            val applicationFullService: ApplicationFullService) {

    @PostMapping("/get")
    fun getApplicationsByEmployeeId(@RequestBody pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationService.getAllByFilter(pageInfo)

    @PostMapping("/count")
    fun getCountByEmployeeId(@RequestBody pageInfo: PageInfo<ApplicationRow>): Mono<Long> = applicationService.getCountByFilter(pageInfo)

    @PostMapping("/getFull")
    fun getFullApplication(@RequestBody id: Long)= applicationFullService.getFullApplication(id)


    @PostMapping("/create")
    fun createApplication(@RequestBody applicationInfo: ApplicationInfo): Mono<Transport> {
        return applicationService.createApplication(applicationInfo)
    }
    @GetMapping("/test")
    fun getTest()=Mono.just("test")

    @ExceptionHandler(value = [WrongDateException::class])
    fun catchWrongDateException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), "WrongDateException::class", ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }

    @ExceptionHandler(value = [ObjectNotFoundException::class])
    fun catchIdNotFoundException(ex: RuntimeException): ResponseEntity<ErrorsDetails> {
        val errorDetails = ErrorsDetails(Date(), "IdNotFoundException::class", ex.message!!)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails)
    }
}
