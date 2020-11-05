package com.pp.trisscore.repository

import com.pp.trisscore.model.classes.AdvanceApplication
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AdvanceApplicationRepostiory : ReactiveCrudRepository<AdvanceApplication,Long>