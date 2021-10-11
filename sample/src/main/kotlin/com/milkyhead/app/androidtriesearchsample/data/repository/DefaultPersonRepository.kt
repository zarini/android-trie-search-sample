package com.milkyhead.app.androidtriesearchsample.data.repository

import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import com.milkyhead.app.trie.Trie
import javax.inject.Inject


class DefaultPersonRepository @Inject constructor(
    private val trie: Trie<Person>
) : PersonRepository {


    override suspend fun insert(persons: List<Person>): Boolean {
        return trie.insert(persons)
    }

    override suspend fun search(query: String): List<Person> {
        return trie.search(query)
    }

    override suspend fun getAll(): List<Person> {
        return trie.all()
    }

    override fun clear() {
        trie.clear()
    }
}