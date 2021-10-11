package com.milkyhead.app.androidtriesearchsample.domain.repository

import com.milkyhead.app.androidtriesearchsample.domain.model.Person


interface PersonLoaderRepository {

    /**
     * Load data from test data set
     */
    suspend fun load(): List<Person>
}