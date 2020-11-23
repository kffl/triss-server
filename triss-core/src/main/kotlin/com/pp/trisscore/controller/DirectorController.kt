package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationFull
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.DirectorService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@CrossOrigin
@RequestMapping("/director")
class DirectorController (val directorService: DirectorService) {


    @PostMapping("application/get")
    fun getApplications(token: JwtAuthenticationToken,
                           @RequestBody pageInfo: PageInfo<ApplicationRow>) = directorService.getApplications(pageInfo, token)


    @PostMapping("application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>,
                         token: JwtAuthenticationToken) = directorService.getCountByFilter(token, body)

    @PostMapping("application/approve")
    fun createApplication(@RequestBody body: ApplicationInfo,
                          token: JwtAuthenticationToken): Mono<Application>
            = directorService.approveApplication(token, body)

    @PostMapping("application/getFull")
    fun getFullApplication(@RequestBody id: Long,
                           token: JwtAuthenticationToken) : Mono<ApplicationInfo>
            = directorService.getFullApplication(token, id)










}

