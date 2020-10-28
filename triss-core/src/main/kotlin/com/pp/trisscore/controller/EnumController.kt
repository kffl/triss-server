package com.pp.trisscore.controller

import com.pp.trisscore.service.EnumService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/enum")
class EnumController(val enumService: EnumService) {

    @GetMapping("/documentType")
    fun getDocumentTypes() = enumService.getDocumentTypes()

    @GetMapping("/paymentType")
    fun getPaymentTypes() = enumService.getPaymentTypes()

    @GetMapping("/status")
    fun getStatuses() = enumService.getStatuses()

    @GetMapping("/vehicle")
    fun getVehicles() = enumService.getVehicles()


}
