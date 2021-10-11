package com.milkyhead.app.androidtriesearchsample.ui.state

import com.milkyhead.app.androidtriesearchsample.domain.model.Person


data class PersonsState(
    val persons: List<Person> = emptyList(),
    val loading: Boolean = false,
    val emptyView: Boolean = persons.isEmpty(),
    val retry: Boolean = false
)