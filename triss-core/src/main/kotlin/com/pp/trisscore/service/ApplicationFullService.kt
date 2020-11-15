package com.pp.trisscore.service

import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.repository.ApplicationFullRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class ApplicationFullService(val applicationFullRepository: ApplicationFullRepository,
                             val transportService: TransportService) {

    fun getFullApplication(id: Long): Mono<ApplicationInfo> {
        val applicationFull = applicationFullRepository.findById(id)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Application")))
        val transportList = transportService.findAllByApplicationID(id)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Transport")))
        return Mono.zip(applicationFull, transportList.collectList()).flatMap { x ->
            Mono.just(ApplicationInfo(
                    application = x.t1.getApplication(),
                    institute = x.t1.getInstitute(),
                    place = x.t1.getPlace(),
                    employee = x.t1.getEmployee(),
                    financialSource = x.t1.getFinancialSource(),
                    advanceApplication = x.t1.getAdvanceApplication(),
                    advancePayments = x.t1.getAdvancePaymentsInfo(),
                    transport = x.t2,
                    identityDocument = x.t1.getIdentityDocument()
            ))
        }
    }
}