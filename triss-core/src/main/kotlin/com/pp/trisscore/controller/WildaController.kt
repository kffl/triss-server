package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.WildaService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@CrossOrigin
@RequestMapping("/wilda")
class WildaController(val wildaService: WildaService) {

    val tokenBody = TokenData(2, "Jan", "Kowalski")


    @PostMapping("/application/get")
    fun getApplications(
//            token: JwtAuthenticationToken,
            @RequestBody pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> {
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getApplications(pageInfo, tokenBody)
    }

    @PostMapping("/application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>
//                         , token: JwtAuthenticationToken
    ): Mono<Long> {
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getCountByFilter(tokenBody, body)
    }

    @PostMapping("application/getFull")
    fun getFullApplication(@RequestBody id: Long
//                           , token: JwtAuthenticationToken
    ) : Mono<ApplicationInfo>{
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.getFullApplication(tokenBody, id)
    }

    @PostMapping("application/approve")
    fun approveApplication(@RequestBody body: ApplicationInfo
//                           , token: JwtAuthenticationToken
    ): Mono<Application> {
//        val tokenBody = tokenService.getEmployeeDataFromToken(token)
        return wildaService.approveApplication(tokenBody, body)
    }


}