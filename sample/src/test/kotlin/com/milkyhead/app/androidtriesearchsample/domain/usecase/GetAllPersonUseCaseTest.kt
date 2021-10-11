package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class GetAllPersonUseCaseTest {

    private lateinit var getAllPersonUseCase: GetAllPersonUseCase
    private lateinit var personRepository: PersonRepository


    @Before
    fun setUp() {
        personRepository = FakePersonRepository()
        getAllPersonUseCase = GetAllPersonUseCase(personRepository)
    }

    @Test
    fun `get all data must return all data`(): Unit = runBlocking {
        personRepository.insert(getPersonList())
        assertThat(getAllPersonUseCase()).hasSize(2)
    }

    private fun getPersonList() = arrayListOf(
        Person("Tito", "Kling", 26),
        Person("Karina", "Goldner", 21),
    )

}