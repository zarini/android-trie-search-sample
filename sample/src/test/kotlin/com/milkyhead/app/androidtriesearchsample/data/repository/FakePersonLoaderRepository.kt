package com.milkyhead.app.androidtriesearchsample.data.repository

import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository


class FakePersonLoaderRepository : PersonLoaderRepository {


    override suspend fun load(): List<Person> = arrayListOf(
        Person("Tito", "Kling", 26),
        Person("Karina", "Goldner", 21),
        Person("Freda", "Kuhn", 29),
        Person("Denis", "Larkin", 39),
        Person("Kris", "Kub", 11)
    )

}