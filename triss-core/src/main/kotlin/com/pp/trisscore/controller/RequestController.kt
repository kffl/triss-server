package com.pp.trisscore.controller

import com.pp.trisscore.model.classes.Request
import com.pp.trisscore.service.RequestService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@RestController
@RequestMapping("requests")
class RequestController(val requestService: RequestService) {

    @PostMapping("/get")
    fun getByEmployeeId(@RequestBody employeeId: Long): Flux<Request> =
            requestService.getByEmployeeId(employeeId)
}