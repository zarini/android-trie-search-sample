package com.milkyhead.app.androidtriesearchsample.domain.usecase

import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import javax.inject.Inject


class LoadPersonsUseCase @Inject constructor(
    private val personLoaderRepository: PersonLoaderRepository
) {

    suspend operator fun invoke(): List<Person> = personLoaderRepository.load()

}