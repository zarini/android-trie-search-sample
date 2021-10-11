package com.milkyhead.app.androidtriesearchsample.data.model

import com.milkyhead.app.androidtriesearchsample.data.datasource.local.entity.PersonEntity
import com.milkyhead.app.androidtriesearchsample.domain.model.Person


fun List<PersonEntity>.mapToDomainPerson(): List<Person> {
    return mutableListOf<Person>().apply {
        for (element in this@mapToDomainPerson) {
            add(element.mapToDomainPerson())
        }
    }
}

private fun PersonEntity.mapToDomainPerson(): Person {
    return Person(firstName, lastName, age)
}