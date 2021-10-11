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

class SearchPersonUseCaseTest {

    private lateinit var searchPersonUseCase: SearchPersonUseCase
    private lateinit var personRepository: PersonRepository


    @Before
    fun setUp() {
        personRepository = FakePersonRepository()
        searchPersonUseCase = SearchPersonUseCase(personRepository)

        runBlocking {
            personRepository.insert(
                arrayListOf(
                    Person("Tito", "Kling", 26),
                    Person("Karina", "Goldner", 21),
                    Person("Freda", "Kuhn", 29),
                    Person("Denis", "Larkin", 39),
                    Person("Tito", "Kling", 26),
                    Person("Kris", "Kub", 11)
                )
            )
        }
    }

    @Test
    fun `search available persons with start chars must return persons list`(): Unit = runBlocking {
        assertThat(searchPersonUseCase("k")).hasSize(2)
    }

}