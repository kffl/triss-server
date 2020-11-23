package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.UserService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@CrossOrigin
@RequestMapping("/user")
class UserController(val userService: UserService) {

    @PostMapping("application/get")
    fun getMyApplications(@RequestBody body: PageInfo<ApplicationRow>,
                          token: JwtAuthenticationToken) = userService.getMyApplications(token, body)

    @PostMapping("application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>,
                             token: JwtAuthenticationToken) = userService.getCountByFilter(token, body)

    @PostMapping("application/create")
    fun createApplication(@RequestBody body: ApplicationInfo,
                          token: JwtAuthenticationToken): Mono<Transport>
        = userService.createApplication(token, body)
    @PostMapping("application/getFull")
    fun getFullApplication(@RequestBody id: Long,
                           token: JwtAuthenticationToken) = userService.getMyFullApplication(token, id)

}