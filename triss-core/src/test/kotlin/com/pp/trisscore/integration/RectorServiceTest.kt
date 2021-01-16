package com.pp.trisscore.integration

import com.pp.trisscore.data.TestData.Companion.correctApplicationForRector
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfoForRector
import com.pp.trisscore.data.TestData.Companion.existingDirectorToken
import com.pp.trisscore.data.TestData.Companion.existingRectorToken
import com.pp.trisscore.data.TestData.Companion.existingUserToken
import com.pp.trisscore.data.TestData.Companion.pageInfo
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.service.RectorService
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

@SpringBootTest
@ActiveProfiles("test")
class RectorServiceTest(@Autowired val rectorService: RectorService,
                        @Autowired val connectionFactory: ConnectionFactory) {

    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()

    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }

    //Rector getCountByFilter
    @Test
    fun shouldGetRectorCount() {
        val x = rectorService.getCountByFilter(existingRectorToken, pageInfo).block()
        assertEquals(4, x)
    }

    @Test
    fun shouldNotGetRectorCountWrongRole() {
        val x = assertThrows<UnauthorizedException> { rectorService.getCountByFilter(existingDirectorToken, pageInfo).block() }
        assertEquals("User 167711 Andrzej Nowak don't have access to this.", x.message)
    }

    //Rector approveApplication
    @Test
    fun shouldNotApproveApplicationWrongRole() {
        val x = assertThrows<UnauthorizedException> {
            rectorService.approveApplication(existingDirectorToken, exampleApplicationInfoForRector).block()
        }
        assertEquals("User 167711 Andrzej Nowak don't have access to this.", x.message)
    }

    @Test
    fun shouldNotApproveApplicationWrongStatus() {
        val x = assertThrows<InvalidRequestBodyException> {
            rectorService.approveApplication(existingRectorToken, exampleApplicationInfoForRector.copy(
                    application = correctApplicationForRector.copy(status = StatusEnum.Accepted.value))).block()
        }
        assertEquals("Status must be WaitingForRector", x.message)
    }

    //Rector rejectApplication
    @Test
    fun shouldNotRejectApplicationWrongRole(){
        val x = assertThrows<UnauthorizedException>{
            rectorService.rejectApplication(existingUserToken, exampleApplicationInfoForRector).block()
        }
        assertEquals("User 170387 Jan Kowalczyk don't have access to this.", x.message)
    }

    @Test
    fun shouldNotRejectApplicationWrongStatus(){
        val x = assertThrows<InvalidRequestBodyException>{
            rectorService.rejectApplication(existingRectorToken, exampleApplicationInfoForRector.copy(
                    application = correctApplicationForRector.copy(status = StatusEnum.Accepted.value))).block()
        }
        assertEquals("Status must be WaitingForRector", x.message)
    }

    @Test
    fun shouldGetApplicationFull() {
        rectorService.getFullApplication(existingRectorToken, 1).block()
    }
}
