package com.milkyhead.app.androidtriesearchsample.domain.usecase

import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import javax.inject.Inject


class InsertPersonsUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {

    suspend operator fun invoke(persons: List<Person>): Boolean = personRepository.insert(persons)
}