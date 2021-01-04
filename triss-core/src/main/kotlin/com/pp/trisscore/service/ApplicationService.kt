package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.classes.Application
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.repository.ApplicationRepository
import com.pp.trisscore.repository.ApplicationRowRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/

@Service
class ApplicationService(
        val applicationRowRepository: ApplicationRowRepository,
        val applicationRepository: ApplicationRepository) {

    fun findById(id:Long)  = applicationRepository.findById(id)
        .switchIfEmpty(Mono.error(ObjectNotFoundException("Request Application")))

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationRowRepository.getAllByFilter(pageInfo)

    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) = applicationRowRepository.getCountByFilter(pageInfo)

    fun saveApplication(application: Application): Mono<Application> = applicationRepository.save(application)

    fun fillApplication(applicationInfo: ApplicationInfo, userId: Long, placeId: Long, prepaymentId: Long, advanceApplicationId: Long, instituteId: Long): Application {
        return applicationInfo.application.copy(createdOn = LocalDate.now(), employeeId = userId, placeId = placeId,
                prepaymentId = prepaymentId, advanceApplicationId = advanceApplicationId, instituteId = instituteId,status = StatusEnum.WaitingForDirector.value)
    }
}
