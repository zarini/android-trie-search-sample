package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class LoadPersonsUseCaseTest {

    private lateinit var loadPersonUseCase: LoadPersonsUseCase
    private lateinit var personLoaderRepository: PersonLoaderRepository


    @Before
    fun setUp() {
        personLoaderRepository = FakePersonLoaderRepository()
        loadPersonUseCase = LoadPersonsUseCase(personLoaderRepository)
    }


    @Test
    fun `load persons from loader must return loaded list`(): Unit = runBlocking {
        assertThat(loadPersonUseCase()).isNotEmpty()
    }

}