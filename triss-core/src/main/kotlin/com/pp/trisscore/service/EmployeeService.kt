package com.pp.trisscore.service

import com.pp.trisscore.exceptions.EmployeeNotFoundException
import com.pp.trisscore.exceptions.InvalidRequestBodyException
import com.pp.trisscore.exceptions.ObjectNotFoundException
import com.pp.trisscore.exceptions.UnauthorizedException
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.repository.EmployeeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class EmployeeService(val employeeRepository: EmployeeRepository,
                      val instituteService: InstituteService) {

    fun findEmployeeAndCheckRole(tokenData: TokenData, role: Role): Mono<Employee?> {
        return findEmployee(tokenData).map { x -> checkRole(x, role) }
                .switchIfEmpty(Mono.error(UnauthorizedException("Employee don't have access to this.")))
    }

    fun findEmployee(t: TokenData) =
            employeeRepository.findById(t.employeeId)
                    .switchIfEmpty(Mono.error(EmployeeNotFoundException("Employee not Found")))


    fun checkRole(employee: Employee, role: Role): Employee? {
        if (employee.employeeType == role)
            return employee
        return null
    }

    fun getEmployee(tokenData: TokenData): Mono<Employee> {
        return findEmployee(tokenData)
    }

    fun saveEmployee(tokenData: TokenData, employee: Employee): Mono<Employee> {
        if (employee.id != tokenData.employeeId)
            throw InvalidRequestBodyException("Id must equal to id in token.")
        if (employee.instituteID == null)
            throw InvalidRequestBodyException("InstituteId cannot be null")
        if (employee.employeeType != Role.USER)
            throw InvalidRequestBodyException("employeeType must be USER")
        if (employee.firstName != tokenData.name)
            throw InvalidRequestBodyException("Employee fistName must equal to name in token")
        if (employee.surname != tokenData.surname)
            throw InvalidRequestBodyException("Employee surname must equal to surname in token")
        return instituteService.findInstituteById(employee.instituteID!!)
                .flatMap { x ->
                    employeeRepository.findById(tokenData.employeeId)
                            .switchIfEmpty(employeeRepository.save(employee.copy(id = tokenData.employeeId)))
                            .flatMap { x -> updateEmployee(x, employee) }
                }
    }

    private fun updateEmployee(actualEmployee: Employee?, newEmployee: Employee): Mono<Employee> {
        if (actualEmployee == null)
            throw ObjectNotFoundException("Employee")
        val employeeToSave = actualEmployee.copy(
                firstName = newEmployee.firstName,
                surname = newEmployee.surname,
                birthDate = newEmployee.birthDate,
                phoneNumber = newEmployee.phoneNumber,
                academicDegree = newEmployee.academicDegree,
                instituteID = newEmployee.instituteID
        )
        return employeeRepository.save(employeeToSave)
    }


}