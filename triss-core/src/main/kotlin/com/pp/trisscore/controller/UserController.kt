package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.TokenService
import com.pp.trisscore.service.UserService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@RestController
@CrossOrigin
@RequestMapping("/user")
class UserController(val userService: UserService,
                     val tokenService: TokenService) {

    @PostMapping("application/get")
    fun getMyApplications(@RequestBody body: PageInfo<ApplicationRow>,
                          token: JwtAuthenticationToken): Flux<ApplicationRow> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getMyApplications(tokenData, body)
    }

    @PostMapping("application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>,
                             token: JwtAuthenticationToken): Mono<Long> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getCountByFilter(tokenData, body)
    }

    @PostMapping("application/getFull")
    fun getFullApplication(@RequestBody id: Long,
                           token: JwtAuthenticationToken): Mono<ApplicationInfo> {
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.getMyFullApplication(tokenData, id)
    }

    @PostMapping("application/create")
    fun createApplication(@RequestBody body: ApplicationInfo,
                          token: JwtAuthenticationToken): Mono<Transport>{
        val tokenData = tokenService.getEmployeeDataFromToken(token)
        return userService.createApplication(tokenData, body)
    }
}