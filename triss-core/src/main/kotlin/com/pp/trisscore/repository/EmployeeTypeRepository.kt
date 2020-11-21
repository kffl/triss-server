package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.EmployeeType
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface EmployeeTypeRepository : ReactiveCrudRepository<EmployeeType,Long>