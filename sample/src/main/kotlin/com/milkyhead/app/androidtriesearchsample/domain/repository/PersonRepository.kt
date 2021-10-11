package com.milkyhead.app.androidtriesearchsample.domain.repository

import com.milkyhead.app.androidtriesearchsample.domain.model.Person


interface PersonRepository {

    /**
     * insert persons data
     */
    suspend fun insert(persons: List<Person>): Boolean

    /**
     * search persons with specific query
     */
    suspend fun search(query: String): List<Person>

    /**
     * get all available persons data
     */
    suspend fun getAll(): List<Person>

    /**
     * clear all persons
     */
    fun clear()
}