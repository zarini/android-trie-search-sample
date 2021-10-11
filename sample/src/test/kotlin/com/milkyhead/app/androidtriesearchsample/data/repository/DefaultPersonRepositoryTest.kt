package com.milkyhead.app.androidtriesearchsample.data.repository


import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import com.milkyhead.app.trie.Trie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DefaultPersonRepositoryTest {

    private lateinit var trie: Trie<Person>
    private lateinit var personRepository: PersonRepository


    @Before
    fun setUp() {
        trie = Trie()
        personRepository = DefaultPersonRepository(trie)
    }

    @Test
    fun `insert persons list to trie must succeed`(): Unit = runBlocking {
        personRepository.insert(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )

        assertThat(trie.length()).isEqualTo(2)
    }

    @Test
    fun `search persons in trie must succeed`(): Unit = runBlocking {
        personRepository.insert(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )

        assertThat(personRepository.search("t")).isEqualTo(trie.search("t"))
    }

    @Test
    fun `get all persons in trie must succeed`(): Unit = runBlocking {
        personRepository.insert(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )

        assertThat(personRepository.getAll()).isEqualTo(trie.all())
    }

    @Test
    fun `clear persons in trie must succeed`(): Unit = runBlocking {
        personRepository.insert(
            arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
        )

        personRepository.clear()
        assertThat(trie.length()).isEqualTo(0)
    }

}