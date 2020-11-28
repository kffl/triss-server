package com.pp.trisscore

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.service.EmployeeService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import reactor.test.StepVerifier
import java.time.LocalDate

@SpringBootTest
@DirtiesContext
class EmployeeTest(@Autowired val employeeService: EmployeeService) {

    val tokenData = TokenData(1999, "Marcel", "TOLEN")
    val employee = Employee(id = null, eLoginId = 1999, firstName = "Marcel", surname = "TOLEN", birthDate = LocalDate.now(), phoneNumber = "666666666", academicDegree = "BRAK", instituteID = 1, employeeType = Role.RECTOR)


    @Test
    @DirtiesContext
    fun shouldCreateUser() {
        val x = employeeService.saveEmployee(tokenData, employee.copy(employeeType = Role.USER))
        StepVerifier.create(x.log()).assertNext { employee -> employee.id != null }.verifyComplete()
    }

    @Test
    @DirtiesContext
    fun shouldNotGetNotExistingEmployeeData() {
        val x = employeeService.findEmployee(tokenData)
        StepVerifier.create(x.log())
                .expectError(EmployeeNotFoundException::class.java)
                .verify()
    }

    @Test
    @DirtiesContext
    fun shouldNotCreateUserAndThrowsInvalidRequestBodyException() {
        assertThrows<InvalidRequestBodyException> { employeeService.saveEmployee(tokenData, employee) }
    }


}
