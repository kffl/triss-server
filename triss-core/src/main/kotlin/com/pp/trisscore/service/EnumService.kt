package com.pp.trisscore.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class EnumService(val statusService: StatusService,
                  val documentTypeService: DocumentTypeService,
                  val paymentTypeService: PaymentTypeService,
                  val vehicleService: VehicleService) {
    fun getDocumentTypes() = documentTypeService.getDocumentTypes().collectList()
    fun getPaymentTypes() = paymentTypeService.getPaymentTypes().collectList()
    fun getStatuses() = statusService.getStatuses().collectList()
    fun getVehicles() = vehicleService.getVehicles().collectList()
    fun getAllEnum(): Any {
        return Mono.zip(getDocumentTypes(), getPaymentTypes(), getStatuses(), getVehicles())
                .map { x -> hashMapOf("documentTypes" to x.t1, "paymentTypes" to x.t2, "statuses" to x.t3, "vehicles" to x.t4) }
    }
}
