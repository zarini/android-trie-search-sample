package com.milkyhead.app.androidtriesearchsample.data.repository


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import com.milkyhead.app.trie.Trie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


class DefaultPersonRepositoryTest {

    private lateinit var trie: Trie<Person>
    private lateinit var personRepository: PersonRepository


    @Before
    fun setUp() {
        trie = Mockito.mock(Trie::class.java) as Trie<Person>
        personRepository = DefaultPersonRepository(trie)
    }

    @Test
    fun `insert persons list must return true`(): Unit = runBlocking {
        Mockito.`when`(trie.insert(any())).thenReturn(true)

        val result = personRepository.insert(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )

        assertThat(result).isTrue()
    }

    @Test
    fun `search persons in must return not empty`(): Unit = runBlocking {
        Mockito.`when`(trie.search("t")).thenReturn(
            arrayListOf(
                Person("Tito", "Kling", 26)
            )
        )
        assertThat(personRepository.search("t")).isEqualTo(
            arrayListOf(Person("Tito", "Kling", 26))
        )
    }

    @Test
    fun `get all persons must return not empty`(): Unit = runBlocking {
        Mockito.`when`(trie.all()).thenReturn(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )

        assertThat(personRepository.getAll()).isEqualTo(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )
    }

    @Test
    fun `clear persons must succeed`(): Unit = runBlocking {
        doNothing().`when`(trie).clear()

        personRepository.clear()

        verify(trie, times(1)).clear()
    }

}