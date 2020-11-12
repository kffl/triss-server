package com.pp.trisscore.service

import com.pp.trisscore.model.classes.Place
import com.pp.trisscore.repository.PlaceRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PlaceService(val placeRepository: PlaceRepository) {
    fun getPlace(place: Place): Mono<Place>
    {
        return placeRepository.findByCityAndCountry(place.city,place.country)
                .switchIfEmpty(placeRepository.save(place))
    }
}