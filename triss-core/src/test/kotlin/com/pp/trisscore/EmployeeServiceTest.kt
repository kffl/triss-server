package com.pp.trisscore

import com.pp.trisscore.exceptions.*
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.service.EmployeeService
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.connectionfactory.init.ScriptUtils
import reactor.core.publisher.Mono
import reactor.kotlin.test.expectError
import reactor.test.StepVerifier
import java.time.LocalDate

@SpringBootTest
class EmployeeServiceTest(@Autowired val employeeService: EmployeeService,
                          @Autowired val connectionFactory: ConnectionFactory) {

    val logger = LoggerFactory.getLogger(EmployeeServiceTest::class.java)

    val newEmployeeTokenData = TokenData(1999, "Marcel", "TOLEN")
    val newEmployee = Employee(id = null, employeeId = 1999, firstName = "Marcel", surname = "TOLEN", birthDate = LocalDate.now(), phoneNumber = "666666666", academicDegree = "BRAK", instituteID = 1, employeeType = Role.USER)
    val existingUserToken = TokenData(170387, "Jan", "Kowalczyk")
    val existingUserEmployee = Employee(id = 1, employeeId = 170387, firstName = "Jan", surname = "Kowalczyk", birthDate = LocalDate.of(2000, 1, 1), phoneNumber = "+48 123456789", academicDegree = "Prof.", instituteID = 1, employeeType = Role.USER)


    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()


    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }

    //Create USER
    @Test
    fun shouldCreateUser() {
        val x = employeeService.newEmployee(newEmployeeTokenData, newEmployee)
        StepVerifier.create(x.log()).assertNext { employee -> assert(employee.id == 5L) }.expectComplete().verify()

    }

    @Test
    fun shouldNotCreateUserBecauseOfEmployeeType() {
        assertThrows<InvalidRequestBodyException> { employeeService.newEmployee(newEmployeeTokenData, newEmployee.copy(employeeType = Role.RECTOR)) }
    }

    @Test
    fun shouldNotCreateUserBecauseWrongUserData() {
        assertThrows<InvalidRequestBodyException> {
            employeeService.newEmployee(existingUserToken, newEmployee)
        }
    }

    @Test
    fun shouldNotCreateUserBecauseAllReadyExists() {
        val x = employeeService.newEmployee(existingUserToken, existingUserEmployee.copy(id = null))
        StepVerifier.create(x.log()).expectError(UserAllReadyExistsException::class).verify()

    }

    //Update User

    @Test
    fun shouldUpdateUser() {
        val x = employeeService.updateEmployee(existingUserToken, existingUserEmployee.copy(phoneNumber = "+48656565654"))
        StepVerifier.create(x.log()).assertNext { x -> assert(x != existingUserEmployee) }
                .expectComplete().verify()
        val y = employeeService.findEmployee(existingUserToken)
        StepVerifier.create(y.log()).assertNext { y -> assert(y == existingUserEmployee.copy(phoneNumber = "+48656565654")) }
                .expectComplete().verify()
    }

    @Test
    fun shouldNotUpdateUserBecauseChangingEmployeeType() {
        val x = employeeService.updateEmployee(existingUserToken, existingUserEmployee.copy(employeeType = Role.RECTOR))
        StepVerifier.create(x.log()).expectError(InvalidRequestBodyException::class).verify()
    }

    @Test
    fun shouldNotUpdateUserBecauseUserDontExists() {
        val x = employeeService.updateEmployee(newEmployeeTokenData, newEmployee.copy(id=10))
        StepVerifier.create(x.log()).expectError(UserNotFoundException::class).verify()
    }


    //Get User Data
    @Test
    fun shouldGetExistingUserData() {
        val x = employeeService.findEmployee(existingUserToken)
        StepVerifier.create(x.log()).assertNext { x -> assert(x == existingUserEmployee) }.expectComplete().verify()
    }

    @Test
    fun shouldNotGetNotExistingInDatabaseEmployeeData() {
        val x = employeeService.findEmployee(newEmployeeTokenData)
        StepVerifier.create(x.log()).expectError(EmployeeNotFoundException::class).verify()
    }

    ////Get User Data And Check Role
    @Test
    fun shouldGetExistingUserDataAndCheckRole() {
        val x = employeeService.findEmployeeAndCheckRole(existingUserToken, Role.USER)
        StepVerifier.create(x.log()).assertNext { x -> assert(x == existingUserEmployee) }.expectComplete().verify()
    }

    @Test
    fun shouldNotGetExistingUserDataAndCheckRoleWrongRole() {
        val x = employeeService.findEmployeeAndCheckRole(existingUserToken, Role.DIRECTOR)
        StepVerifier.create(x.log()).expectError(UnauthorizedException::class).verify()
    }

    @Test
    fun shouldNotGetExistingUserDataAndCheckRoleNotExistingUser() {
        val x = employeeService.findEmployeeAndCheckRole(newEmployeeTokenData, Role.DIRECTOR)
        StepVerifier.create(x.log()).expectError(UnauthorizedException::class).verify()
    }
}
