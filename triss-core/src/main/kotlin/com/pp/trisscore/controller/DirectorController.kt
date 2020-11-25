package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.DirectorService
import com.pp.trisscore.service.TokenService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@CrossOrigin
@RequestMapping("/director")
class DirectorController (val directorService: DirectorService,
                          val tokenService: TokenService) {

    @PostMapping("application/get")
    fun getApplications(token: JwtAuthenticationToken,
                           @RequestBody pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.getApplications(pageInfo, tokenBody)
    }

    @PostMapping("application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>,
                         token: JwtAuthenticationToken): Mono<Long> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.getCountByFilter(tokenBody, body)
    }

    @PostMapping("application/approve")
    fun createApplication(@RequestBody body: ApplicationInfo,
                          token: JwtAuthenticationToken): Mono<Application> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.approveApplication(tokenBody, body)
    }

    @PostMapping("application/getFull")
    fun getFullApplication(@RequestBody id: Long,
                           token: JwtAuthenticationToken) : Mono<ApplicationInfo>{
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return directorService.getFullApplication(tokenBody, id)
    }
}

