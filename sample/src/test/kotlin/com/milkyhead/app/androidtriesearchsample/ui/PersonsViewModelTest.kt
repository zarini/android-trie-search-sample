package com.milkyhead.app.androidtriesearchsample.ui

import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.CoroutineDispatchersRule
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.usecase.*
import com.milkyhead.app.androidtriesearchsample.ui.event.PersonsEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class PersonsViewModelTest {

    private lateinit var personLoaderRepository: PersonLoaderRepository
    private lateinit var personRepository: PersonRepository

    private lateinit var loadPersonsUseCase: LoadPersonsUseCase
    private lateinit var clearAllUseCase: ClearAllUseCase
    private lateinit var getAllPersonUseCase: GetAllPersonUseCase
    private lateinit var insertPersonsUseCase: InsertPersonsUseCase
    private lateinit var searchPersonUseCase: SearchPersonUseCase

    private lateinit var personsViewModel: PersonsViewModel

    @get:Rule
    val coroutineRule = CoroutineDispatchersRule()


    @Before
    fun setUp() {
        personLoaderRepository = Mockito.mock(PersonLoaderRepository::class.java)
        personRepository = Mockito.mock(PersonRepository::class.java)

        loadPersonsUseCase = LoadPersonsUseCase(personLoaderRepository)
        clearAllUseCase = ClearAllUseCase(personRepository)
        getAllPersonUseCase = GetAllPersonUseCase(personRepository)
        insertPersonsUseCase = InsertPersonsUseCase(personRepository)
        searchPersonUseCase = SearchPersonUseCase(personRepository)

        personsViewModel = PersonsViewModel(
            loadPersonsUseCase,
            insertPersonsUseCase,
            getAllPersonUseCase,
            searchPersonUseCase,
            clearAllUseCase,
            coroutineRule.dispatcher
        )
    }

    @Test
    fun `load person on start view model must succeed`(): Unit = coroutineRule.runBlockingTest {

        val data = arrayListOf(
            Person("Tito", "Kling", 26),
            Person("Karina", "Goldner", 21),
        )

        `when`(personLoaderRepository.load()).thenReturn(data)

        `when`(personRepository.insert(any())).thenReturn(true)

        `when`(personRepository.getAll()).thenReturn(data)

        personsViewModel.onStart()

        verify(personLoaderRepository, times(1)).load()
        verify(personRepository, times(1)).insert(data)
        verify(personRepository, times(1)).getAll()

        assertThat(personsViewModel.state.value.persons.size).isEqualTo(2)
        assertThat(personsViewModel.state.value.loading).isFalse()
        assertThat(personsViewModel.state.value.emptyView).isFalse()
        assertThat(personsViewModel.state.value.retry).isFalse()
    }

    @Test
    fun `search person must update state succeed`(): Unit = coroutineRule.runBlockingTest {

        `when`(personRepository.search(any())).thenReturn(
            arrayListOf(
                Person("Tito", "Kling", 26)
            )
        )

        personsViewModel.event(PersonsEvent.Search("Ti"))

        verify(personRepository, times(1)).search("Ti")

        assertThat(personsViewModel.state.value.persons.size).isEqualTo(1)
        assertThat(personsViewModel.state.value.persons[0]).isEqualTo(
            Person("Tito", "Kling", 26)
        )
        assertThat(personsViewModel.state.value.loading).isFalse()
        assertThat(personsViewModel.state.value.emptyView).isFalse()
        assertThat(personsViewModel.state.value.retry).isFalse()
    }

    @Test
    fun `clear all data when view model stop`(): Unit = coroutineRule.runBlockingTest {
        personsViewModel.onStop()
        verify(personRepository, times(1)).clear()
    }
}