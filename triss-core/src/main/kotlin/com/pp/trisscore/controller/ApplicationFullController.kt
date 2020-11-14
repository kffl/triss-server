package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.GetApplicationFullRequestBody
import com.pp.trisscore.model.rows.ApplicationFull
import com.pp.trisscore.service.ApplicationFullService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@CrossOrigin
@RequestMapping("/applicationfull")
class ApplicationFullController(val applicationFullService: ApplicationFullService) {


    @PostMapping("/get")
    fun getApplicationsByEmployeeId(@RequestBody a:GetApplicationFullRequestBody): Mono<ApplicationInfo> = applicationFullService.findById(a.applicationId)

}