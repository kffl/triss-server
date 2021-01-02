package com.pp.trisscore.integration

import com.pp.trisscore.service.InstituteService
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.connectionfactory.init.ScriptUtils
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono

@SpringBootTest
@ActiveProfiles("test")
class InstituteServiceTest(@Autowired val instituteService: InstituteService,
                           @Autowired val connectionFactory: ConnectionFactory) {



    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()

    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }

    @Test
    fun shouldGetAllInstitutesOrderByInstituteName() {
        val ins = instituteService.getAllInstitute().collectList().block()
        assertNotNull(ins)
        assertEquals("Instytut Analizy Konstrukcji", ins?.get(0)?.name);
    }
}
