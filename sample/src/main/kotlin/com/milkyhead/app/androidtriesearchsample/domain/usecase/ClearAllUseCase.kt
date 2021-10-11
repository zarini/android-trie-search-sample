package com.milkyhead.app.androidtriesearchsample.domain.usecase

import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import javax.inject.Inject


class ClearAllUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {

    operator fun invoke() = personRepository.clear()
}