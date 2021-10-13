package com.milkyhead.app.androidtriesearchsample.data.repository

import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository


class FakePersonLoaderRepository : PersonLoaderRepository {

    override suspend fun load(): List<Person> = emptyList()
}