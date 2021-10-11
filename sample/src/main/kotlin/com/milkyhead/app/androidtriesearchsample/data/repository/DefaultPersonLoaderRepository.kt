package com.milkyhead.app.androidtriesearchsample.data.repository

import com.milkyhead.app.androidtriesearchsample.data.datasource.local.FilePersonLoader
import com.milkyhead.app.androidtriesearchsample.data.model.mapToDomainPerson
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import javax.inject.Inject


class DefaultPersonLoaderRepository @Inject constructor(
    private val filePersonLoader: FilePersonLoader
) : PersonLoaderRepository {


    override suspend fun load(): List<Person> {
        return filePersonLoader.load().mapToDomainPerson()
    }
}