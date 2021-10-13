package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SearchPersonUseCaseTest {

    private lateinit var searchPersonUseCase: SearchPersonUseCase
    private lateinit var personRepository: PersonRepository


    @Before
    fun setUp() {
        personRepository = Mockito.mock(PersonRepository::class.java)
        searchPersonUseCase = SearchPersonUseCase(personRepository)
    }

    @Test
    fun `search must call repository search succeed`(): Unit = runBlocking {
        searchPersonUseCase("k")
        verify(personRepository, times(1)).search("k")
    }

}