package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


class LoadPersonsUseCaseTest {

    private lateinit var loadPersonUseCase: LoadPersonsUseCase
    private lateinit var personLoaderRepository: PersonLoaderRepository


    @Before
    fun setUp() {
        personLoaderRepository = Mockito.mock(PersonLoaderRepository::class.java)
        loadPersonUseCase = LoadPersonsUseCase(personLoaderRepository)
    }


    @Test
    fun `load persons from loader must call repository load succeed`(): Unit = runBlocking {
        loadPersonUseCase()
        verify(personLoaderRepository, times(1)).load()
    }

}