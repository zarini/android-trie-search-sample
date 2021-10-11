package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class InsertPersonsUseCaseTest {

    private lateinit var insertPersonsUseCase: InsertPersonsUseCase
    private lateinit var personRepository: PersonRepository

    @Before
    fun setUp() {
        personRepository = FakePersonRepository()
        insertPersonsUseCase = InsertPersonsUseCase(personRepository)
    }

    @Test
    fun `insert persons list must succeed`(): Unit = runBlocking {
        val inserted = insertPersonsUseCase(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )

        assertThat(inserted).isTrue()
    }
}