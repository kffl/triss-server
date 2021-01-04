package com.pp.trisscore.integration

import com.pp.trisscore.service.EnumService
import io.r2dbc.spi.ConnectionFactory
import kotlinx.coroutines.reactive.collect
import org.junit.jupiter.api.Assertions
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
class EnumServiceTest(@Autowired val enumService: EnumService,
                      @Autowired val connectionFactory: ConnectionFactory){

    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()

    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }

    @Test
    fun shouldGetDocumentTypes(){
        val enumDocTypes = enumService.getDocumentTypes().block()
        Assertions.assertNotNull(enumDocTypes)
        Assertions.assertEquals("Dowód osobisty", enumDocTypes?.get(0)?.namePl)
    }

    @Test
    fun shouldGetPaymentTypes(){
        val enumPaymentTypes = enumService.getPaymentTypes().block()
        Assertions.assertNotNull(enumPaymentTypes)
        Assertions.assertEquals("Gotówka", enumPaymentTypes?.get(0)?.namePl)
    }

    @Test
    fun shouldGetStatuses(){
        val enumStatuses = enumService.getStatuses().block()
        Assertions.assertNotNull(enumStatuses)
        Assertions.assertEquals("Edycja", enumStatuses?.get(0)?.namePl)
    }

    @Test
    fun shouldGetVehicles(){
        val enumVehicles = enumService.getVehicles().block()
        Assertions.assertNotNull(enumVehicles)
        Assertions.assertEquals("Samolot", enumVehicles?.get(0)?.namePl)
    }
}