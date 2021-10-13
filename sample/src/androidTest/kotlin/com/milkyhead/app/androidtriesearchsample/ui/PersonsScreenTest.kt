package com.milkyhead.app.androidtriesearchsample.ui


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.filters.SmallTest
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.usecase.*
import com.milkyhead.app.androidtriesearchsample.ui.theme.AndroidTrieSearchSampleTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any


@SmallTest
@HiltAndroidTest
class PersonsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: PersonsViewModel

    @Before
    fun setUp() {
        val personLoaderRepository = Mockito.mock(PersonLoaderRepository::class.java)
        val personRepository = Mockito.mock(PersonRepository::class.java)

        val loadPersonsUseCase = LoadPersonsUseCase(personLoaderRepository)
        val clearAllUseCase = ClearAllUseCase(personRepository)
        val getAllPersonUseCase = GetAllPersonUseCase(personRepository)
        val insertPersonsUseCase = InsertPersonsUseCase(personRepository)
        val searchPersonUseCase = SearchPersonUseCase(personRepository)

        viewModel = PersonsViewModel(
            loadPersonsUseCase,
            insertPersonsUseCase,
            getAllPersonUseCase,
            searchPersonUseCase,
            clearAllUseCase,
            Dispatchers.Main
        )

        runBlocking {
            val data = arrayListOf(
                Person("Tito", "Kling", 26),
                Person("Karina", "Goldner", 21),
            )
            Mockito.`when`(personLoaderRepository.load()).thenReturn(data)
            Mockito.`when`(personRepository.insert(any())).thenReturn(true)
            Mockito.`when`(personRepository.getAll()).thenReturn(data)
        }

        composeRule.setContent {
            AndroidTrieSearchSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PersonsScreen(viewModel)
                }
            }
        }
    }

    @Test
    fun personScreenSearchbar_exist() {
        composeRule.onNodeWithContentDescription("Searchbar").assertExists()
    }

    @Test
    fun personScreenListOfPerson_MustShownAfterLoad() {
        composeRule.onNodeWithContentDescription("PersonList").assertExists()
    }

}