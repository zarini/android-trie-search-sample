package com.milkyhead.app.androidtriesearchsample.data.repository

import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository


class FakePersonRepository : PersonRepository {

    override suspend fun insert(persons: List<Person>): Boolean = true

    override suspend fun search(query: String): List<Person> = emptyList()

    override suspend fun getAll(): List<Person> = emptyList()

    override fun clear() {
    }
}