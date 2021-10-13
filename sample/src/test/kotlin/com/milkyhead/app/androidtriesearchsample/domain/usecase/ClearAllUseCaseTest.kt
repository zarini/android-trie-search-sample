package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.kotlin.times


class ClearAllUseCaseTest {

    private lateinit var clearAllUseCase: ClearAllUseCase
    private lateinit var personRepository: PersonRepository

    @Before
    fun setUp() {
        personRepository = Mockito.mock(PersonRepository::class.java)
        clearAllUseCase = ClearAllUseCase(personRepository)
    }


    @Test
    fun `clear must call repository clear succeed`() {
        clearAllUseCase()
        verify(personRepository, times(1)).clear()
    }

}