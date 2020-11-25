package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.RectorService
import com.pp.trisscore.service.TokenService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@CrossOrigin
@RequestMapping("/rector")
class RectorController(val rectorService: RectorService,
                       private val tokenService: TokenService){

    @PostMapping("/application/get")
    fun getApplications(token: JwtAuthenticationToken,
                        @RequestBody pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return rectorService.getApplications(pageInfo, tokenBody)
    }

    @PostMapping("/application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>,
                         token: JwtAuthenticationToken): Mono<Long> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return rectorService.getCountByFilter(tokenBody, body)
    }

}
