package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.ApplicationService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@RestController
@RequestMapping("/application")
class ApplicationController(val applicationService: ApplicationService) {

    @PostMapping("/get") //TODO Long will be changed to filter object in future
    fun getApplicationsByEmployeeId(@RequestBody pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationService.getAllByFilter(pageInfo);

    @PostMapping("/count") //TODO PageInfo will be changed to filter object in future
    fun getCountByEmployeeId(@RequestBody pageInfo: PageInfo<ApplicationRow>): Mono<Long> = applicationService.getCountByFilter(pageInfo)

}
