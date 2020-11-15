package com.pp.trisscore.service

import com.pp.trisscore.model.applicationinfoelements.AdvancePaymentsInfo
import com.pp.trisscore.model.architecture.ApplicationInfo
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.model.classes.IdentityDocument
import com.pp.trisscore.model.classes.Transport
import com.pp.trisscore.model.rows.ApplicationFull
import com.pp.trisscore.repository.ApplicationFullRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ApplicationFullService(val applicationFullRepository: ApplicationFullRepository,
                             val transportService: TransportService) {

}