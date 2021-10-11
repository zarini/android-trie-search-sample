package com.milkyhead.app.androidtriesearchsample.ui

import com.google.common.truth.Truth.assertThat
import com.milkyhead.app.androidtriesearchsample.CoroutineDispatchersRule
import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.data.repository.FakePersonRepository
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
        personLoaderRepository = FakePersonLoaderRepository()
        personRepository = FakePersonRepository()
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
    fun load_persons_on_start_view_model(): Unit = coroutineRule.runBlockingTest {

        personsViewModel.onStart()
        val result = getAllPersonUseCase()

        assertThat(personsViewModel.state.value.persons.size).isEqualTo(result.size)

        for (index in result.indices) {
            assertThat(personsViewModel.state.value.persons[index]).isEqualTo(result[index])
        }

        assertThat(personsViewModel.state.value.loading).isFalse()
        assertThat(personsViewModel.state.value.emptyView).isFalse()
        assertThat(personsViewModel.state.value.retry).isFalse()
    }

    @Test
    fun search_persons_event(): Unit = coroutineRule.runBlockingTest {
        personsViewModel.onStart()
        personsViewModel.event(PersonsEvent.Search("Ti"))

        assertThat(personsViewModel.state.value.persons.size).isEqualTo(1)
        assertThat(personsViewModel.state.value.persons[0]).isEqualTo(
            Person("Tito", "Kling", 26)
        )
        assertThat(personsViewModel.state.value.loading).isFalse()
        assertThat(personsViewModel.state.value.emptyView).isFalse()
        assertThat(personsViewModel.state.value.retry).isFalse()
    }

    @Test
    fun clear_persons_when_view_model_stop(): Unit = coroutineRule.runBlockingTest {
        personsViewModel.onStart()
        personsViewModel.onStop()

        assertThat(getAllPersonUseCase()).isEmpty()
    }
}