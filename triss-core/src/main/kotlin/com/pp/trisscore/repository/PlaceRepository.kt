package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.Place
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface PlaceRepository : ReactiveCrudRepository<Place,Long> {

    fun findByCityAndCountry(City:String,Country:String):Mono<Place>
}