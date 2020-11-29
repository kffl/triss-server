package com.pp.trisscore

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.service.EmployeeService
import io.r2dbc.spi.ConnectionFactory
import org.springframework.core.io.Resource
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.r2dbc.connectionfactory.init.ScriptUtils
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDate

@SpringBootTest
class EmployeeServiceTest(@Autowired val employeeService: EmployeeService,
                   @Autowired val connectionFactory: ConnectionFactory) {

    val tokenData = TokenData(1999, "Marcel", "TOLEN")
    val employee = Employee(id = null, employeeId = 1999, firstName = "Marcel", surname = "TOLEN", birthDate = LocalDate.now(), phoneNumber = "666666666", academicDegree = "BRAK", instituteID = 1, employeeType = Role.RECTOR)


    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()


    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script:Resource){ executeScriptBlocking(script)}


    @Test
    fun shouldCreateUser() {
        val x = employeeService.saveEmployee(tokenData, employee.copy(employeeType = Role.USER))
        StepVerifier.create(x.log()).assertNext { employee -> employee.id != null }.verifyComplete()
    }

    @Test
    fun shouldNotGetNotExistingEmployeeData() {
        val x = employeeService.findEmployee(tokenData)
        StepVerifier.create(x.log())
                .expectError(EmployeeNotFoundException::class.java)
                .verify()
    }

    @Test
    fun shouldNotCreateUserAndThrowsInvalidRequestBodyException() {
        assertThrows<InvalidRequestBodyException> { employeeService.saveEmployee(tokenData, employee) }
    }


}
