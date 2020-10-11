package com.pp.trisscore.controller

import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.architecture.RESTResponse
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.service.ApplicationService
import org.springframework.web.bind.annotation.*

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
    fun getByEmployeeId(@RequestBody pageInfo: PageInfo<ApplicationRow>): RESTResponse<ApplicationRow> {
        var response: RESTResponse<ApplicationRow>
        try {
            response = RESTResponse(
                    data = applicationService.getAllByFilter(pageInfo),
                    count = applicationService.getCountByFilter(pageInfo),
                    isSuccess = true
            )
        }
        catch(e: Exception) {
            response = RESTResponse(
                    data = null,
                    count = null,
                    isSuccess = false
            )
        }
        return response
    }

}
