package com.milkyhead.app.androidtriesearchsample.domain.usecase


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.data.repository.DefaultPersonRepository
import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import com.milkyhead.app.trie.Trie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class ClearAllUseCaseTest {

    private lateinit var clearAllUseCase: ClearAllUseCase
    private lateinit var personRepository: PersonRepository

    @Before
    fun setUp() {
        personRepository = FakePersonRepository()
        clearAllUseCase = ClearAllUseCase(personRepository)

        runBlocking {
            personRepository.insert(
                arrayListOf(
                    Person("Tito", "Kling", 26),
                    Person("Karina", "Goldner", 21),
                )
            )
        }
    }


    @Test
    fun `clear must remove all person in structure`(): Unit = runBlocking {
        clearAllUseCase()
        assertThat(personRepository.getAll()).isEmpty()
    }

}