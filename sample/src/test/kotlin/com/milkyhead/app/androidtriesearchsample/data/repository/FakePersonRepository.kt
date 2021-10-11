package com.milkyhead.app.androidtriesearchsample.data.repository

import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository


class FakePersonRepository : PersonRepository {

    private val persons = arrayListOf<Person>()


    override suspend fun insert(persons: List<Person>): Boolean {
        return this.persons.addAll(persons)
    }

    override suspend fun search(query: String): List<Person> {
        return persons.filter { it.key.lowercase().startsWith(query.lowercase()) }
    }

    override suspend fun getAll(): List<Person> {
        return persons
    }

    override fun clear() {
        persons.clear()
    }
}