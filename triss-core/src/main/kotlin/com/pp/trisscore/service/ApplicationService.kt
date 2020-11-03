package com.pp.trisscore.service

import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.rows.ApplicationRow
import com.pp.trisscore.repository.ApplicationRowRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

/**
 *
 * author: Marceli Jerzyński
 * e-mail: marceli.jerzynski@student.put.poznan.pl
 * Date: 06.10.2020
 **/
@Service
class ApplicationService(
        val applicationRowRepository: ApplicationRowRepository) {

    fun getAllByFilter(pageInfo: PageInfo<ApplicationRow>): Flux<ApplicationRow> = applicationRowRepository.getAllByFilter(pageInfo)
    fun getCountByFilter(pageInfo: PageInfo<ApplicationRow>) = applicationRowRepository.getCountByFilter(pageInfo)
    fun createApplication(applicationInfo: ApplicationInfo){
        validateApplication(applicationInfo)








    }
    fun validateApplication(applicationInfo: ApplicationInfo)
    {
        //TODO
    }
}
