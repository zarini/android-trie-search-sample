package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


class InsertPersonsUseCaseTest {

    private lateinit var insertPersonsUseCase: InsertPersonsUseCase
    private lateinit var personRepository: PersonRepository

    @Before
    fun setUp() {
        personRepository = Mockito.mock(PersonRepository::class.java)
        insertPersonsUseCase = InsertPersonsUseCase(personRepository)
    }

    @Test
    fun `insert persons list must call repository insert succeed`(): Unit = runBlocking {
        insertPersonsUseCase(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )
        verify(personRepository, times(1)).insert(any())
    }
}