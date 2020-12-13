package com.pp.trisscore.controller

import com.pp.trisscore.service.InstituteService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@CrossOrigin
@RequestMapping("/institute")
data class InstituteController(private val instituteService: InstituteService)
{
    @GetMapping("/all")
    fun getAllInstitute()=instituteService.getAllInstitute().collectList()
}
