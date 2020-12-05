package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.TokenService
import com.pp.trisscore.service.WildaService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@CrossOrigin
@RestController
@RequestMapping("/wilda")
class WildaController(private val wildaService: WildaService,
                      private val tokenService: TokenService) {


    @PostMapping("/application/get")
    fun getApplications(
            @RequestBody pageInfo: PageInfo<ApplicationRow>,
            token: JwtAuthenticationToken
    ): Mono<List<ApplicationRow>> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getApplications(pageInfo, tokenBody).collectList()
    }

    @PostMapping("/application/count")
    fun getCountByFilter(
                         @RequestBody body: PageInfo<ApplicationRow>,
                         token: JwtAuthenticationToken
    ): Mono<Long> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getCountByFilter(tokenBody, body)
    }

    @PostMapping("application/getFull")
    fun getFullApplication(
                           @RequestBody id: Long,
                           token: JwtAuthenticationToken
    ): Mono<ApplicationInfo>{
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getFullApplication(tokenBody, id)
    }

    @PostMapping("application/approve")
    fun approveApplication(
                           @RequestBody body: ApplicationInfo,
                           token: JwtAuthenticationToken
    ): Mono<Application> {
        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.approveApplication(tokenBody, body)
    }
}
