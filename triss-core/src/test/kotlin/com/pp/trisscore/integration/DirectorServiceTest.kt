package com.pp.trisscore.integration

import com.pp.trisscore.data.TestData.Companion.existingDirector
import com.pp.trisscore.data.TestData.Companion.existingDirectorToken
import com.pp.trisscore.data.TestData.Companion.existingUserToken
import com.pp.trisscore.data.TestData.Companion.pageInfo
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.service.DirectorService
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.connectionfactory.init.ScriptUtils
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono
import java.lang.RuntimeException


@SpringBootTest
@ActiveProfiles("test")
class DirectorServiceTest(@Autowired val directorService: DirectorService,
                          @Autowired val connectionFactory: ConnectionFactory) {

    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()

    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }
    //Director getCountByFilter
    @Test
    fun shouldGetDirectorCount() {
        val x = directorService.getCountByFilter(existingDirectorToken, pageInfo).block()
        assertEquals(1,x)
    }

    @Test
    fun shouldNotGetDirectorCountWrongRole() {
        val x = assertThrows<UnauthorizedException> { directorService.getCountByFilter(existingUserToken, pageInfo).block() }
        assertEquals("Employee don't have access to this.",x.message)
    }
}
