package com.pp.trisscore.integration

import com.pp.trisscore.data.TestData.Companion.correctApplicationForWaitingForWilda
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfoForWaitingForWilda
import com.pp.trisscore.data.TestData.Companion.existingDirectorToken
import com.pp.trisscore.data.TestData.Companion.existingUserToken
import com.pp.trisscore.data.TestData.Companion.existingWildaToken
import com.pp.trisscore.data.TestData.Companion.pageInfo
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.enums.Status
import com.pp.trisscore.service.WildaService
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
class WildaServiceTest(@Autowired val wildaService: WildaService,
                       @Autowired val connectionFactory: ConnectionFactory) {

    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()

    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }

    //Wilda getCountByFilter

    /*
    @Test
    //TODO Wilda should get application with 2 types of status: WaitingForWilda and Accepted
    fun shouldGetWildaCount() {
        val x = wildaService.getCountByFilter(existingWildaToken, pageInfo).block()
        Assertions.assertEquals(2, x)
    }
    */

    @Test
    fun shouldNotGetWildaCountWrongRole() {
        val x = assertThrows<UnauthorizedException> { wildaService.getCountByFilter(existingDirectorToken, pageInfo).block() }
        assertEquals("User 167711 Andrzej Nowak don't have access to this.", x.message)
    }

    //Wilda approveApplication
    @Test
    fun shouldNotApproveApplicationWrongRole() {
        val x = assertThrows<UnauthorizedException> {
            wildaService.approveApplication(existingDirectorToken, exampleApplicationInfoForWaitingForWilda).block()
        }
        assertEquals("User 167711 Andrzej Nowak don't have access to this.", x.message)
    }

    @Test
    fun shouldNotApproveApplicationWrongStatus() {
        val x = assertThrows<InvalidRequestBodyException> {
            wildaService.approveApplication(existingWildaToken, exampleApplicationInfoForWaitingForWilda.copy(
                    application = correctApplicationForWaitingForWilda.copy(status = Status.WaitingForRector))).block()
        }
        assertEquals("Status must be WaitingForWilda", x.message)
    }

    //Wilda rejectApplication
    @Test
    fun shouldNotRejectApplicationWrongRole(){
        val x = assertThrows<UnauthorizedException>{
            wildaService.rejectApplication(existingUserToken, exampleApplicationInfoForWaitingForWilda).block()
        }
        assertEquals("User 170387 Jan Kowalczyk don't have access to this.", x.message)
    }

    @Test
    fun shouldNotRejectApplicationWrongStatus(){
        val x = assertThrows<InvalidRequestBodyException>{
            wildaService.rejectApplication(existingWildaToken, exampleApplicationInfoForWaitingForWilda.copy(
                    application = correctApplicationForWaitingForWilda.copy(status = Status.WaitingForRector))).block()
        }
        assertEquals("Status must be WaitingForWilda", x.message)
    }

    @Test
    fun shouldGetApplicationFull() {
        wildaService.getFullApplication(existingWildaToken, 1).block()
    }
}
