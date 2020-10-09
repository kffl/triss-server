package com.pp.trisscore.controller

import com.pp.trisscore.model.requestbody.ApplicationRequestBody
import com.pp.trisscore.service.ApplicationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@RestController
@RequestMapping("/application")
class ApplicationController(val applicationService: ApplicationService) {

    @PostMapping("/get")
    fun getByEmployeeId(@RequestBody applicationRequestBody: ApplicationRequestBody) =
            applicationService.getAllByEmployeeID(applicationRequestBody.employeeId)

}
