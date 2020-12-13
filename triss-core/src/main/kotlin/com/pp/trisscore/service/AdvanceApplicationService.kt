package com.pp.trisscore.service

import com.pp.trisscore.model.classes.AdvanceApplication
import com.pp.trisscore.repository.AdvanceApplicationRepostiory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class AdvanceApplicationService(val advanceApplicationRepostiory: AdvanceApplicationRepostiory) {
    fun createAdvanceApplication(advanceApplication: AdvanceApplication, placeId: Long): Mono<AdvanceApplication> {
        return advanceApplicationRepostiory.save(advanceApplication.copy(placeId = placeId))
    }
}
