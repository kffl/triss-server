package com.pp.trisscore.repository

import com.pp.trisscore.model.enums.*
import org.springframework.stereotype.Repository

@Repository
class EnumRepository{

    fun getPaymentTypes(): List<EnumModel> {
        val paymentTypesList:MutableList<EnumModel> = mutableListOf()
        for(paymentType in PaymentType.values()){
            paymentTypesList.add(EnumModel(paymentType.ordinal, paymentType.namePl, paymentType.nameEng))
        }
        return paymentTypesList
    }

    fun getStatuses(): List<EnumModel> {
        val statusesList:MutableList<EnumModel> = mutableListOf()
        for(status in Status.values()){
            statusesList.add(EnumModel(status.ordinal, status.namePl, status.nameEng))
        }
        return statusesList
    }

    fun getVehicles(): List<EnumModel> {
        val vehiclesList:MutableList<EnumModel> = mutableListOf()
        for(vehicle in Vehicle.values()){
            vehiclesList.add(EnumModel(vehicle.ordinal, vehicle.namePl, vehicle.nameEng))
        }
        return vehiclesList
    }
}
