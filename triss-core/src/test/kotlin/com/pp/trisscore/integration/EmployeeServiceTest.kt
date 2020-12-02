package com.pp.trisscore.integration

import com.pp.trisscore.exceptions.*
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.repository.EmployeeRepository
import com.pp.trisscore.service.EmployeeService
import com.pp.trisscore.data.TestData.Companion.existingUserEmployee
import com.pp.trisscore.data.TestData.Companion.existingUserToken
import com.pp.trisscore.data.TestData.Companion.newEmployee
import com.pp.trisscore.data.TestData.Companion.newEmployeeTokenData
import io.r2dbc.spi.ConnectionFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
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
class EmployeeServiceTest(@Autowired val employeeService: EmployeeService,
                          @Autowired val connectionFactory: ConnectionFactory,
                          @Autowired val employeeRepository: EmployeeRepository) {


    fun executeScriptBlocking(sqlScript: Resource) = Mono.from(connectionFactory.create())
            .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
            .block()


    @BeforeEach
    fun prepareDatabase(@Value("classpath:db/prepareTestData.sql") script: Resource) {
        executeScriptBlocking(script)
    }

    //Create Employee
    @Test
    fun shouldCreateEmployee() {
        assertEquals(5, employeeService.newEmployee(newEmployeeTokenData, newEmployee).block()!!.id)
        assertEquals(5, employeeRepository.count().block())

    }

    @Test
    fun shouldNotCreateEmployeeBecauseOfEmployeeType() {
        val x = assertThrows<InvalidRequestBodyException> { employeeService.newEmployee(newEmployeeTokenData, newEmployee.copy(employeeType = Role.RECTOR)).block() }
        assertEquals("employeeType must be USER", x.message)
    }

    @Test
    fun shouldNotCreateEmployeeBecauseWrongUserData() {
        val x = assertThrows<InvalidRequestBodyException> { employeeService.newEmployee(existingUserToken, newEmployee).block() }
        assertEquals("EmployeeId must equal to id in token.", x.message)
    }

    @Test
    fun shouldNotCreateEmployeeBecauseAlreadyExists() {
        val x = assertThrows<UserAllReadyExistsException> { employeeService.newEmployee(existingUserToken, existingUserEmployee.copy(id = null)).block() }
        assertEquals("User All Ready Exists", x.message)
    }

    //Update Employee
    @Test
    fun shouldUpdateEmployee() {
        val x = employeeService.updateEmployee(existingUserToken, existingUserEmployee.copy(phoneNumber = "+48656565654")).block()
        assertNotEquals(existingUserEmployee, x)
        val y = employeeService.findEmployee(existingUserToken).block()
        assertEquals(existingUserEmployee.copy(phoneNumber = "+48656565654"), y)
    }

    @Test
    fun shouldNotUpdateEmployeeBecauseChangingEmployeeType() {
        val x = assertThrows<InvalidRequestBodyException> {
            employeeService.updateEmployee(existingUserToken,
                    existingUserEmployee.copy(employeeType = Role.RECTOR)).block()
        }
        assertEquals("Employee Type cannot be updated by user", x.message)
    }

    @Test
    fun shouldNotUpdateEmployeeBecauseUserDontExists() {
        val x = assertThrows<UserNotFoundException> { employeeService.updateEmployee(newEmployeeTokenData, newEmployee.copy(id = 10)).block() }
        assertEquals("User not exists", x.message)
    }


    //Get Employee Data
    @Test
    fun shouldGetExistingEmployeeData() {
        val x = employeeService.findEmployee(existingUserToken).block()
        assertEquals(existingUserEmployee, x)
    }

    @Test
    fun shouldNotGetNotExistingInDatabaseEmployeeData() {
        val x = assertThrows<EmployeeNotFoundException> { employeeService.findEmployee(newEmployeeTokenData).block()}
        assertEquals("Employee not Found", x.message)
    }

    ////Get Employee Data And Check Role
    @Test
    fun shouldGetExistingEmployeeDataAndCheckRole() {
        val x = employeeService.findEmployeeAndCheckRole(existingUserToken, Role.USER).block()
        assertEquals(existingUserEmployee,x)
    }

    @Test
    fun shouldNotGetExistingEmployeeDataAndCheckRoleWrongRole() {
        val x = assertThrows<UnauthorizedException> {  employeeService.findEmployeeAndCheckRole(existingUserToken, Role.DIRECTOR).block()}
        assertEquals("Employee don't have access to this.", x.message)
    }

    @Test
    fun shouldNotGetExistingEmployeeDataAndCheckRoleNotExistingUser() {
        val x = assertThrows<UnauthorizedException> { employeeService.findEmployeeAndCheckRole(newEmployeeTokenData, Role.DIRECTOR).block()}
        assertEquals("Employee don't have access to this.", x.message)
    }
}
