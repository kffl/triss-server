package com.pp.trisscore

import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.classes.FinancialSource
import com.pp.trisscore.model.enums.Status
import com.pp.trisscore.service.TestData.Companion.correctAdvanceApplication
import com.pp.trisscore.service.TestData.Companion.correctAdvancePaymentsInfo
import com.pp.trisscore.service.TestData.Companion.correctInstitute
import com.pp.trisscore.service.TestData.Companion.correctPlace
import com.pp.trisscore.service.TestData.Companion.exampleApplicationInfo
import com.pp.trisscore.service.TestData.Companion.existingDirector
import com.pp.trisscore.service.TestData.Companion.existingDirectorToken
import com.pp.trisscore.service.TestData.Companion.existingUserEmployee
import com.pp.trisscore.service.TestData.Companion.existingUserToken
import com.pp.trisscore.service.TestData.Companion.filter
import com.pp.trisscore.service.TestData.Companion.getExampleForUserApplication
import com.pp.trisscore.service.TestData.Companion.pageInfo
import com.pp.trisscore.service.UserService
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.connectionfactory.init.ScriptUtils
import org.springframework.test.context.ActiveProfiles
import reactor.core.publisher.Mono
import java.time.LocalDate


@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest(@Autowired val userService: UserService,
                      @Autowired val connectionFactory: ConnectionFactory) {


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
        val before = userService.getCountByFilter(existingUserToken, pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))).block()
        assertEquals(1, before)
    }

    @Test
    fun shouldGetAllApplicationDirector() {
        val before = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        assertEquals(0, before)
    }

    @Test
    fun shouldNotGetAllApplicationWrongIdInFilter() {
        val x = assertThrows<InvalidRequestBodyException> { userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))).block() }
        assertEquals("Wrong EmployeeId.", x.message)
    }


    //Create Application Tests
    @Test
    fun shouldCreateNewApplicationUser() {
        val before = userService.getCountByFilter(existingUserToken, pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))).block()
        val id = userService.createApplication(existingUserToken, exampleApplicationInfo.copy(application = getExampleForUserApplication(existingUserEmployee))).block()!!.applicationID
        val after = userService.getCountByFilter(existingUserToken, pageInfo.copy(filter = filter.copy(employeeId = existingUserToken.employeeId))).block()
        assertEquals(before, after!! - 1)
        val app = userService.getMyFullApplication(existingUserToken, id!!).block()
        assertEquals(getExampleForUserApplication(existingUserEmployee).copy(id = app!!.application.id, placeId = app.application.placeId, advanceApplicationId = app.application.advanceApplicationId,
                prepaymentId = app.application.prepaymentId, employeeId = existingUserToken.employeeId, createdOn = LocalDate.now()), app.application)
        assertEquals(correctAdvanceApplication.copy(id = app.advanceApplication.id, placeId = app.advanceApplication.placeId), app.advanceApplication)
        assertEquals(correctAdvancePaymentsInfo, app.advancePayments)
        assertEquals(correctPlace.copy(id = app.place.id), app.place)
        assertEquals(FinancialSource(null, null, null, null, null), app.financialSource)
        assertEquals(correctInstitute, app.institute)
        assertEquals(2, app.transport.size)
    }

    @Test
    fun shouldCreateNewApplicationDirector() {
        val before = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        val id = userService.createApplication(existingDirectorToken, exampleApplicationInfo.copy(application = getExampleForUserApplication(existingDirector))).block()!!.applicationID
        val after = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        assertEquals(after!! - 1, before)
        val app = userService.getMyFullApplication(existingDirectorToken, id!!).block()
        assertEquals(getExampleForUserApplication(existingDirector).copy(id = app!!.application.id, placeId = app.application.placeId, advanceApplicationId = app.application.advanceApplicationId,
                prepaymentId = app.application.prepaymentId, employeeId = existingDirectorToken.employeeId, createdOn = LocalDate.now()), app.application)
        assertEquals(correctAdvanceApplication.copy(id = app.advanceApplication.id, placeId = app.advanceApplication.placeId), app.advanceApplication)
        assertEquals(correctAdvancePaymentsInfo, app.advancePayments)
        assertEquals(correctPlace.copy(id = app.place.id), app.place)
        assertEquals(FinancialSource(null, null, null, null, null), app.financialSource)
        assertEquals(correctInstitute, app.institute)
        assertEquals(2, app.transport.size)
    }

    @Test
    fun shouldNotCreateNewApplicationWrongUserDataInApplication() {
        val before = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        val x = assertThrows<InvalidRequestBodyException> { userService.createApplication(existingDirectorToken, exampleApplicationInfo.copy(application = getExampleForUserApplication(existingUserEmployee))).block()!!.applicationID }
        assertEquals("FirstName is not equal first name in database", x.message)
        val after = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        assertEquals(after, before)
    }

    @Test
    fun shouldNotCreateNewApplicationWrongFinancialSourceDataInApplication() {
        val before = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        val x = assertThrows<InvalidRequestBodyException> { userService.createApplication(existingDirectorToken, exampleApplicationInfo.copy(application = getExampleForUserApplication(existingDirector), financialSource = FinancialSource(id = null, allocationAccount = "blabla", mpk = "notnull", financialSource = "finsource", project = "project"))).block()!!.applicationID }
        assertEquals("FinancialSource must be null", x.message)
        val after = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        assertEquals(after, before)
    }

    @Test
    fun shouldNotCreateNewApplicationWrongStatusInApplication() {
        val before = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        val x = assertThrows<InvalidRequestBodyException> { userService.createApplication(existingDirectorToken, exampleApplicationInfo.copy(application = getExampleForUserApplication(existingDirector).copy(status = Status.Accepted))).block()!!.applicationID }
        assertEquals("Application Status must be WaitingForDirector", x.message)
        val after = userService.getCountByFilter(existingDirectorToken, pageInfo.copy(filter = filter.copy(employeeId = existingDirectorToken.employeeId))).block()
        assertEquals(after, before)
    }


}
