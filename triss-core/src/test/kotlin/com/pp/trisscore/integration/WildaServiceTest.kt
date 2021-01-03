package com.pp.trisscore.integration

import com.pp.trisscore.data.TestData.Companion.correctApplicationForWaitingForWilda
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfoForWaitingForWilda
import com.pp.trisscore.data.TestData.Companion.existingDirectorToken
import com.pp.trisscore.data.TestData.Companion.existingWildaToken
import com.pp.trisscore.data.TestData.Companion.pageInfo
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.architecture.PageInfo
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.model.rows.SettlementApplicationRow
import com.pp.trisscore.service.WildaService
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.connectionfactory.init.ScriptUtils
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono

@SpringBootTest
@ActiveProfiles("test")
class WildaServiceTest(
    @Autowired val wildaService: WildaService,
    @Autowired val connectionFactory: ConnectionFactory
) {

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
        val x = assertThrows<UnauthorizedException> {
            wildaService.getCountByFilter(existingDirectorToken, pageInfo).block()
        }
        Assertions.assertEquals("User 167711 Andrzej Nowak don't have access to this.", x.message)
    }

    //Wilda approveApplication
    @Test
    fun shouldNotApproveApplicationWrongRole() {
        val x = assertThrows<UnauthorizedException> {
            wildaService.approveApplication(existingDirectorToken, exampleApplicationInfoForWaitingForWilda).block()
        }
        Assertions.assertEquals("User 167711 Andrzej Nowak don't have access to this.", x.message)
    }

    @Test
    fun shouldNotApproveApplicationWrongStatus() {
        val x = assertThrows<InvalidRequestBodyException> {
            wildaService.approveApplication(
                existingWildaToken, exampleApplicationInfoForWaitingForWilda.copy(
                    application = correctApplicationForWaitingForWilda.copy(status = StatusEnum.WaitingForRector.value)
                )
            ).block()
        }
        Assertions.assertEquals("Status must be WaitingForWilda", x.message)
    }

    @Test
    fun shouldGetApplicationFull() {
        wildaService.getFullApplication(existingWildaToken, 1).block()
    }


    //Settlement Application Tests

    @Test
    fun shouldApproveSettlementApplication() {
        val x = wildaService.getSettlementApplication(existingWildaToken, 2).block()
        assertDoesNotThrow {
            wildaService.changeSettlementApplicationStatus(
                existingWildaToken,
                x!!,
                StatusEnum.Accepted.value
            ).block()
        }
    }

    @Test
    fun shouldThrowByApprovingSettlementApplication_WrongSettlementStatus() {
        val x = wildaService.getSettlementApplication(existingWildaToken, 1).block()
        val y = assertThrows<InvalidRequestBodyException> {
            wildaService.changeSettlementApplicationStatus(
                existingWildaToken,
                x!!,
                StatusEnum.Accepted.value
            ).block()
        }
        assertEquals("Status must Be WaitingForWilda", y.message)
    }

    @Test
    fun shouldThrowByApprovingSettlementApplication_SettlementNotExists() {
        val x = wildaService.getSettlementApplication(existingWildaToken, 2).block()
        val y = assertThrows<ObjectNotFoundException> {
            wildaService.changeSettlementApplicationStatus(
                existingWildaToken,
                x!!.copy(fullSettlementApplication = x.fullSettlementApplication.copy(id = 17)),
                StatusEnum.Accepted.value
            ).block()
        }
        assertEquals("Settlement Application not found.", y.message)
    }


    @Test
    fun shouldRejectSettlementApplication() {
        val x = wildaService.getSettlementApplication(existingWildaToken, 2).block()
        assertDoesNotThrow {
            wildaService.changeSettlementApplicationStatus(
                existingWildaToken,
                x!!.copy(fullSettlementApplication = x.fullSettlementApplication.copy(wildaComments = "bo tak :(")),
                StatusEnum.RejectedByWilda.value
            ).block()
        }
    }


    @Test
    fun shouldThrowByRejectingSettlementApplication_MissingComment() {
        val x = wildaService.getSettlementApplication(existingWildaToken, 2).block()
        val y = assertThrows<InvalidRequestBodyException> {
            wildaService.changeSettlementApplicationStatus(
                existingWildaToken,
                x!!,
                StatusEnum.RejectedByWilda.value
            )
        }
        assertEquals("wildaComments cannot be null", y.message)
    }

    @Test
    fun shouldGetSettlementApplications() {
        val x = wildaService.getSettlementApplications(
            PageInfo(
                SettlementApplicationRow(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                ), false, "id", 100, 0
            ), existingWildaToken
        ).collectList().block()
        assertEquals(1,x!!.size)
    }

    @Test
    fun shouldGetSettlementApplication() {
        val x = wildaService.getSettlementApplication(existingWildaToken,2).block()
        assertNotNull(x)
    }


}
