package com.pp.trisscore.service

import com.pp.trisscore.exceptions.*
import com.pp.trisscore.model.architecture.TokenData
import com.pp.trisscore.model.classes.Employee
import com.pp.trisscore.model.enums.Role
import com.pp.trisscore.repository.EmployeeRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class EmployeeService(val employeeRepository: EmployeeRepository,
                      val instituteService: InstituteService) {

    fun findEmployeeAndCheckRole(tokenData: TokenData, role: Role): Mono<Employee> {
        return employeeRepository.findByEmployeeIdAndEmployeeType(tokenData.employeeId,role)
                .switchIfEmpty(Mono.error(UnauthorizedException("Employee don't have access to this.")))
    }

    fun findEmployee(t: TokenData) =
            employeeRepository.findByEmployeeId(t.employeeId)
                    .switchIfEmpty(Mono.error(EmployeeNotFoundException("Employee not Found")))

    fun updateEmployee(tokenData: TokenData, employee: Employee): Mono<Employee> {
        if (employee.id == null)
            throw InvalidRequestBodyException("Id cannot be null")
        if (employee.employeeId != tokenData.employeeId)
            throw InvalidRequestBodyException("Id must equal to id in token.")
        if (employee.instituteID == null)
            throw InvalidRequestBodyException("InstituteId cannot be null")
        if (employee.firstName != tokenData.name)
            throw InvalidRequestBodyException("Employee fistName must equal to name in token")
        if (employee.surname != tokenData.surname)
            throw InvalidRequestBodyException("Employee surname must equal to surname in token")
        return instituteService.findInstituteById(employee.instituteID)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Institute Not Exists.")))
                .flatMap { x -> employeeRepository.findByEmployeeId(tokenData.employeeId) }
                .switchIfEmpty(Mono.error { UserNotFoundException("User not exists") })
                .flatMap { actualEmployee -> updateEmployee2(actualEmployee, employee) }
    }

    private fun updateEmployee2(actualEmployee: Employee?, employee: Employee): Mono<Employee> {
        if (actualEmployee!!.employeeType != employee.employeeType)
            throw InvalidRequestBodyException("Employee Type cannot be updated by user")
        val employeeToSave = actualEmployee.copy(
                firstName = employee.firstName,
                surname = employee.surname,
                birthDate = employee.birthDate,
                phoneNumber = employee.phoneNumber,
                academicDegree = employee.academicDegree,
                instituteID = employee.instituteID
        )
        return employeeRepository.save(employeeToSave)
    }


    fun newEmployee(tokenData: TokenData, employee: Employee): Mono<Employee> {
        if (employee.id != null)
            throw InvalidRequestBodyException("Id must be null")
        if (employee.employeeId != tokenData.employeeId)
            throw InvalidRequestBodyException("EmployeeId must equal to id in token.")
        if (employee.instituteID == null)
            throw InvalidRequestBodyException("InstituteId cannot be null")
        if (employee.firstName != tokenData.name)
            throw InvalidRequestBodyException("Employee fistName must equal to name in token")
        if (employee.surname != tokenData.surname)
            throw InvalidRequestBodyException("Employee surname must equal to surname in token")
        if (employee.employeeType != Role.USER)
            throw InvalidRequestBodyException("employeeType must be USER")
        return instituteService.findInstituteById(employee.instituteID)
                .switchIfEmpty(Mono.error(ObjectNotFoundException("Institute Not Exists.")))
                .flatMap { x -> employeeRepository.findByEmployeeId(tokenData.employeeId) }
                .flatMap { x -> ifUserFoundThrowException(x) }
                .switchIfEmpty(employeeRepository.save(employee))
    }

    fun ifUserFoundThrowException(employee: Employee): Mono<Employee>
            = throw UserAllReadyExistsException("User All Ready Exists")


}
