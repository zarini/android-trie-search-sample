package com.milkyhead.app.androidtriesearchsample.ui.event

import com.milkyhead.app.androidtriesearchsample.domain.model.Person


sealed class PersonsEvent {
    object LoadPersons : PersonsEvent()
    data class Search(val query: String) : PersonsEvent()
    data class SelectPerson(val person: Person): PersonsEvent()
}
