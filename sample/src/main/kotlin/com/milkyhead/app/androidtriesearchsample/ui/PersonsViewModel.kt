package com.milkyhead.app.androidtriesearchsample.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.milkyhead.app.androidtriesearchsample.di.MainDispatcher
import com.milkyhead.app.androidtriesearchsample.domain.usecase.*
import com.milkyhead.app.androidtriesearchsample.ui.event.PersonsEvent
import com.milkyhead.app.androidtriesearchsample.ui.state.PersonsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PersonsViewModel @Inject constructor(
    private val loadPersonsUseCase: LoadPersonsUseCase,
    private val insertPersonsUseCase: InsertPersonsUseCase,
    private val getAllPersonUseCase: GetAllPersonUseCase,
    private val searchPersonUseCase: SearchPersonUseCase,
    private val clearAllUseCase: ClearAllUseCase,
    @MainDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _state = mutableStateOf(PersonsState(loading = true))
    val state: State<PersonsState> = _state


    fun onStart() {
        loadPersons()
    }

    fun onStop() {
        clearAllUseCase()
    }

    fun event(event: PersonsEvent) {
        when (event) {

            is PersonsEvent.LoadPersons -> {
                loadPersons()
            }

            is PersonsEvent.Search -> {
                viewModelScope.launch(dispatcher) {
                    val result = searchPersonUseCase(event.query)
                    _state.value = state.value.copy(
                        persons = result,
                        loading = false,
                        emptyView = result.isEmpty(),
                        retry = false
                    )
                }
            }

//            is PersonsEvent.SelectPerson -> {
//                val person = event.person
//            }
        }
    }

    private fun loadPersons() {
        viewModelScope.launch(dispatcher) {
            val persons = loadPersonsUseCase()
            val loaded = insertPersonsUseCase(persons)

            if (loaded) {
                val result = getAllPersonUseCase()
                _state.value = state.value.copy(
                    persons = result,
                    loading = false,
                    emptyView = false,
                )
            } else {
                _state.value = PersonsState(
                    loading = false,
                    retry = true
                )
            }
        }
    }
}