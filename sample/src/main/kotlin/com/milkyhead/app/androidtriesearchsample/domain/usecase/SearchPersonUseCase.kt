package com.milkyhead.app.androidtriesearchsample.domain.usecase

import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import javax.inject.Inject


class SearchPersonUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {

    suspend operator fun invoke(query: String) = personRepository.search(query)
}