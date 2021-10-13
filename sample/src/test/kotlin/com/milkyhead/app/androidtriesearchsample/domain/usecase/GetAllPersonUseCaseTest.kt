package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.kotlin.times


class GetAllPersonUseCaseTest {

    private lateinit var getAllPersonUseCase: GetAllPersonUseCase
    private lateinit var personRepository: PersonRepository


    @Before
    fun setUp() {
        personRepository = Mockito.mock(PersonRepository::class.java)
        getAllPersonUseCase = GetAllPersonUseCase(personRepository)
    }

    @Test
    fun `get all must call repository get all succeed`(): Unit = runBlocking {
        getAllPersonUseCase()
        verify(personRepository, times(1)).getAll()
    }
}