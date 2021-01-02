package com.pp.trisscore.integration

import com.pp.trisscore.data.TestData.Companion.correctAdvanceApplication
import com.pp.trisscore.data.TestData.Companion.correctAdvancePaymentsInfo
import com.pp.trisscore.data.TestData.Companion.correctInstitute
import com.pp.trisscore.data.TestData.Companion.correctPlace
import com.pp.trisscore.data.TestData.Companion.exampleApplicationInfo
import com.pp.trisscore.data.TestData.Companion.existingDirector
import com.pp.trisscore.data.TestData.Companion.existingDirectorToken
import com.pp.trisscore.data.TestData.Companion.existingUserEmployee
import com.pp.trisscore.data.TestData.Companion.existingUserToken
import com.pp.trisscore.data.TestData.Companion.filter
import com.pp.trisscore.data.TestData.Companion.getExampleForUserApplication
import com.pp.trisscore.data.TestData.Companion.pageInfo
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.RequestDataDiffersFromDatabaseDataException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.model.classes.SettlementElement
import com.pp.trisscore.model.enums.StatusEnum
import com.pp.trisscore.repository.FullSettlementApplicationRepository
import com.pp.trisscore.repository.SettlementElementRepository
import com.pp.trisscore.service.UserService
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.connectionfactory.init.ScriptUtils
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate


@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest(
    @Autowired val userService: UserService,
    @Autowired val settlementElementRepository: SettlementElementRepository,
    @Autowired val fullSettlementApplicationRepository: FullSettlementApplicationRepository,
    @Autowired val connectionFactory: ConnectionFactory
) {


    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
        .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
        .block()


    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }

    //Get Application Test
    @Test
    fun shouldGetAllApplicationUser() {
        val before = userService.getCountByFilter(
            existingUserToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))
        ).block()
        assertEquals(7, before)
    }

    @Test
    fun shouldGetAllApplicationDirector() {
        val before = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        assertEquals(0, before)
    }

    @Test
    fun shouldNotGetAllApplicationWrongIdInFilter() {
        val x = assertThrows<InvalidRequestBodyException> {
            userService.getCountByFilter(
                existingDirectorToken,
                pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))
            ).block()
        }
        assertEquals("Wrong EmployeeId.", x.message)
    }


    //Create Application Tests
    @Test
    fun shouldCreateNewApplicationUser() {
        val before = userService.getCountByFilter(
            existingUserToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))
        ).block()
        val id = userService.createApplication(
            existingUserToken,
            exampleApplicationInfo.copy(application = getExampleForUserApplication(existingUserEmployee))
        ).block()!!.applicationID
        val after = userService.getCountByFilter(
            existingUserToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))
        ).block()
        assertEquals(before, after!! - 1)
        val app = userService.getMyFullApplication(existingUserToken, id!!).block()
        assertEquals(
            getExampleForUserApplication(existingUserEmployee).copy(
                id = app!!.application.id,
                placeId = app.application.placeId,
                advanceApplicationId = app.application.advanceApplicationId,
                prepaymentId = app.application.prepaymentId,
                employeeId = existingUserToken.employeeId,
                createdOn = LocalDate.now()
            ), app.application
        )
        assertEquals(
            correctAdvanceApplication.copy(
                id = app.advanceApplication.id,
                placeId = app.advanceApplication.placeId
            ), app.advanceApplication
        )
        assertEquals(correctAdvancePaymentsInfo, app.advancePayments)
        assertEquals(correctPlace.copy(id = app.place.id), app.place)
        assertEquals(FinancialSource(null, null, null, null, null), app.financialSource)
        assertEquals(correctInstitute, app.institute)
        assertEquals(2, app.transport.size)
    }

    @Test
    fun shouldCreateNewApplicationDirector() {
        val before = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        val id = userService.createApplication(
            existingDirectorToken,
            exampleApplicationInfo.copy(application = getExampleForUserApplication(existingDirector))
        ).block()!!.applicationID
        val after = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        assertEquals(after!! - 1, before)
        val app = userService.getMyFullApplication(existingDirectorToken, id!!).block()
        assertEquals(
            getExampleForUserApplication(existingDirector).copy(
                id = app!!.application.id,
                placeId = app.application.placeId,
                advanceApplicationId = app.application.advanceApplicationId,
                prepaymentId = app.application.prepaymentId,
                employeeId = existingDirectorToken.employeeId,
                createdOn = LocalDate.now()
            ), app.application
        )
        assertEquals(
            correctAdvanceApplication.copy(
                id = app.advanceApplication.id,
                placeId = app.advanceApplication.placeId
            ), app.advanceApplication
        )
        assertEquals(correctAdvancePaymentsInfo, app.advancePayments)
        assertEquals(correctPlace.copy(id = app.place.id), app.place)
        assertEquals(FinancialSource(null, null, null, null, null), app.financialSource)
        assertEquals(correctInstitute, app.institute)
        assertEquals(2, app.transport.size)
    }

    @Test
    fun shouldNotCreateNewApplicationWrongUserDataInApplication() {
        val before = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        val x = assertThrows<RequestDataDiffersFromDatabaseDataException> {
            userService.createApplication(
                existingDirectorToken,
                exampleApplicationInfo.copy(application = getExampleForUserApplication(existingUserEmployee))
            ).block()!!.applicationID
        }
        assertEquals("FirstName in DB differs from the request's FirstName", x.message)
        val after = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        assertEquals(after, before)
    }

    @Test
    fun shouldNotCreateNewApplicationWrongFinancialSourceDataInApplication() {
        val before = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        val x = assertThrows<InvalidRequestBodyException> {
            userService.createApplication(
                existingDirectorToken,
                exampleApplicationInfo.copy(
                    application = getExampleForUserApplication(existingDirector),
                    financialSource = FinancialSource(
                        id = null,
                        allocationAccount = "blabla",
                        mpk = "notnull",
                        financialSource = "finsource",
                        project = "project"
                    )
                )
            ).block()!!.applicationID
        }
        assertEquals("FinancialSource must be null", x.message)
        val after = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        assertEquals(after, before)
    }

    @Test
    fun shouldNotCreateNewApplicationWrongStatusInApplication() {
        val before = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        val x = assertThrows<InvalidRequestBodyException> {
            userService.createApplication(
                existingDirectorToken,
                exampleApplicationInfo.copy(application = getExampleForUserApplication(existingDirector).copy(status = StatusEnum.Accepted.value))
            ).block()!!.applicationID
        }
        assertEquals("Application Status must be WaitingForDirector", x.message)
        val after = userService.getCountByFilter(
            existingDirectorToken,
            pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))
        ).block()
        assertEquals(after, before)
    }


    //Settlement Application Tests
    @Test
    fun shouldCreateNewSettlementApplication() =
        assertDoesNotThrow { userService.createSettlementApplication(existingUserToken, 6).block() }


    @Test
    fun shouldThrowByCreatingNewSettlementApplication_SettlementAllReadyExists() {
        val x = assertThrows<InvalidRequestBodyException> {
            userService.createSettlementApplication(existingUserToken, 7).block()
        }
        assertEquals("Settlement all ready exists", x.message)
    }

    @Test
    fun shouldThrowByCreatingNewSettlementApplication_WrongUser() {
        val x = assertThrows<UnauthorizedException> {
            userService.createSettlementApplication(existingDirectorToken, 6).block()
        }
        assertEquals("This is not a user {167711,Andrzej,Nowak} application", x.message)
    }

    @Test
    fun shouldSendToWildaNewSettlementApplication() {
        val y = userService.sendToWildaSettlementApplication(existingUserToken, 1).block()
        assertEquals(2, y!!.status)
    }

    @Test
    fun shouldThrowBySendingToWildaNewSettlementApplication_WrongSettlementStatus() {
        val y = assertThrows<InvalidRequestBodyException> {
            userService.sendToWildaSettlementApplication(
                existingUserToken,
                2
            ).block()
        }
        assertEquals("Status must be Edit or RejectedByWilda", y.message)
    }

    @Test
    fun shouldThrowBySendingToWildaNewSettlementApplication_SettlementNotExists() {
        val y = assertThrows<ObjectNotFoundException> {
            userService.sendToWildaSettlementApplication(existingUserToken, 3).block()
        }
        assertEquals("Settlement Application not found.", y.message)
    }


    @Test
    fun shouldAddToSettlementApplicationNewElement() {
        userService.addSettlementElement(
            existingUserToken, SettlementElement(
                null, 1, "documentNumber",
                BigDecimal.valueOf(100).setScale(2), "SOMETHING", null
            )
        ).block()
        val a = userService.getSettlementApplication(existingUserToken, 1).block()
        assertEquals(
            3, a!!.settlementElements.size
        )
    }

    @Test
    fun shouldThrowByAddingNewApplicationElement_BlankDocumentNumber() {
        val x = assertThrows<InvalidRequestBodyException> {
            userService.addSettlementElement(
                existingUserToken, SettlementElement(
                    null, 1, " ",
                    BigDecimal.valueOf(100).setScale(2), "SOMETHING", null
                )
            ).block()
        }
        assertEquals("DocumentNumber cannot be blank", x.message)
    }

    @Test
    fun shouldThrowByAddingNewApplicationElement_InvalidSettlementApplicationId() {
        val x = assertThrows<ObjectNotFoundException> {
            userService.addSettlementElement(
                existingUserToken, SettlementElement(
                    null, 4, "documentNumber",
                    BigDecimal.valueOf(100).setScale(2), "SOMETHING", null
                )
            ).block()
        }
        assertEquals("Settlement Application not found.", x.message)
    }

    @Test
    fun shouldThrowByAddingNewApplicationElement_WrongUser() {
        val x = assertThrows<UnauthorizedException> {
            userService.addSettlementElement(
                existingDirectorToken, SettlementElement(
                    null, 1, "documentNumber",
                    BigDecimal.valueOf(100).setScale(2), "SOMETHING", null
                )
            ).block()
        }
        assertEquals("This is not a user {167711,Andrzej,Nowak} Settlement", x.message)
    }

    @Test
    fun shouldThrowByAddingNewApplicationElement_WrongStatus() {
        val x = assertThrows<InvalidRequestBodyException> {
        userService.addSettlementElement(
            existingUserToken, SettlementElement(
                null, 2, "documentNumber",
                BigDecimal.valueOf(100).setScale(2), "SOMETHING", null
            )
        ).block()
        }
        assertEquals("Status must be Edit or RejectedByWilda", x.message)
    }

    @Test
    fun shouldThrowByCreatingNewSettlementApplication_WrongApplicationStatus() {
        val x = assertThrows<InvalidRequestBodyException> {
            userService.createSettlementApplication(existingUserToken, 5).block()
        }
        assertEquals("Status must be Accepted", x.message)
    }

}
