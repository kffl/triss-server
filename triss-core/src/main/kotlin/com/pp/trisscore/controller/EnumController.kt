package com.pp.trisscore.controller

import com.pp.trisscore.service.EnumService
import net.minidev.json.JSONArray
import net.minidev.json.JSONObject
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@CrossOrigin
@RequestMapping("/enum")
class EnumController(private val enumService: EnumService) {

    @GetMapping("/documentType")
    fun getDocumentTypes() = enumService.getDocumentTypes()

    @GetMapping("/paymentType")
    fun getPaymentTypes() = enumService.getPaymentTypes()

    @GetMapping("/status")
    fun getStatuses() = enumService.getStatuses()

    @GetMapping("/vehicle")
    fun getVehicles() = enumService.getVehicles()

    @GetMapping
    fun getAllEnum() = enumService.getAllEnum()
}
