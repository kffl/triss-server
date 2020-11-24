package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.RectorService
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@CrossOrigin
@RequestMapping("/rector")
class RectorController(val rectorService: RectorService){
    @PostMapping("/application/get")
    fun getApplications(token: JwtAuthenticationToken,
                        @RequestBody pageInfo: PageInfo<ApplicationRow>) = rectorService.getApplications(pageInfo, token)

    @PostMapping("/application/count")
    fun getCountByFilter(@RequestBody body: PageInfo<ApplicationRow>,
                         token: JwtAuthenticationToken) = rectorService.getCountByFilter(token, body)

}
