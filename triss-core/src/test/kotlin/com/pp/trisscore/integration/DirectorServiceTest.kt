package com.pp.trisscore.integration

import com.pp.trisscore.data.TestData.Companion.correctApplicationForDirector
import com.pp.trisscore.data.TestData.Companion.correctFinancialSource
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfoForDirector
import com.pp.trisscore.data.TestData.Companion.existingDirectorToken
import com.pp.trisscore.data.TestData.Companion.existingUserToken
import com.pp.trisscore.data.TestData.Companion.pageInfo
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.enums.Status
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
    //TODO Director should see applications only from his institute and with status WaitingForDirector
    /*
    @Test
    fun shouldGetDirectorCount() {
        val x = directorService.getCountByFilter(existingDirectorToken, pageInfo).block()
        assertEquals(2, x)
    }
    */

    @Test
    fun shouldNotGetDirectorCountWrongRole() {
        val x = assertThrows<UnauthorizedException> { directorService.getCountByFilter(existingUserToken, pageInfo).block() }
        assertEquals("Employee don't have access to this.", x.message)
    }

    //Director approveApplication
    @Test
    fun shouldNotApproveApplicationNullFinancialSource() {
        val x = assertThrows<InvalidRequestBodyException> { directorService.approveApplication(existingDirectorToken, exampleApplicationInfoForDirector).block() }
        assertEquals("FinancialSource cannot be null", x.message)
    }

    @Test
    fun shouldNotApproveApplicationWrongRole() {
        val x = assertThrows<UnauthorizedException> {
            directorService.approveApplication(existingUserToken,
                    exampleApplicationInfoForDirector.copy(financialSource = correctFinancialSource)).block()
        }
        assertEquals("Employee don't have access to this.", x.message)
    }

    @Test
    fun shouldNotApproveApplicationWrongStatus() {

        val x = assertThrows<UnauthorizedException> {
            directorService.approveApplication(existingDirectorToken,
                    exampleApplicationInfoForDirector.copy(financialSource = correctFinancialSource, application = correctApplicationForDirector
                            .copy(status = Status.WaitingForWilda))).block()
        }
        assertEquals("Status must be WaitingForDirector", x.message)
    }

    //Director rejectApplication
    @Test
    fun shouldNotRejectApplicationWrongRole(){
        val x = assertThrows<UnauthorizedException>{
            directorService.rejectApplication(existingUserToken, exampleApplicationInfoForDirector).block()
        }
        assertEquals("Employee don't have access to this.", x.message)
    }

    @Test
    fun shouldNotRejectApplicationWrongStatus(){
        val x = assertThrows<UnauthorizedException>{
            directorService.rejectApplication(existingDirectorToken, exampleApplicationInfoForDirector.copy(
                    application = correctApplicationForDirector.copy(status = Status.WaitingForWilda))).block()
        }
        assertEquals("Status must be WaitingForDirector", x.message)
    }

    @Test
    fun shouldGetApplicationFull() {
        directorService.getFullApplication(existingDirectorToken, 1).block()
    }
}
