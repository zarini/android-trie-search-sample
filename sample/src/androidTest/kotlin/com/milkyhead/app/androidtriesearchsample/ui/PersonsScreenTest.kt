package com.milkyhead.app.androidtriesearchsample.ui


import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.milkyhead.app.androidtriesearchsample.R
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonLoaderRepository
import com.milkyhead.app.androidtriesearchsample.domain.repository.PersonRepository
import com.milkyhead.app.androidtriesearchsample.domain.usecase.*
import com.milkyhead.app.androidtriesearchsample.ui.theme.AndroidTrieSearchSampleTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class PersonsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var viewModel: PersonsViewModel
    private lateinit var context: Context

    @Mock
    lateinit var personLoaderRepository: PersonLoaderRepository
    @Mock
    lateinit var personRepository: PersonRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        context = ApplicationProvider.getApplicationContext()

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
    }

    @Test
    fun loadEmptyPersonData_searchbarAndEmptyViewAndRetryButton_mustVisible() = runBlockingTest {
        Mockito.`when`(personLoaderRepository.load()).thenReturn(emptyList())
        Mockito.`when`(personRepository.insert(any())).thenReturn(false)
        Mockito.`when`(personRepository.getAll()).thenReturn(emptyList())

        setComposeContent()

        composeRule.onNodeWithContentDescription("Searchbar").assertExists()
        composeRule.onNodeWithText(context.getString(R.string.empty_message)).assertExists()
        composeRule.onNodeWithText(context.getString(R.string.retry)).assertExists()
    }

    @Test
    fun loadPersonsData_searchbarAndPersonsRow_mustVisible() = runBlockingTest {
        val data = arrayListOf(
            Person("Tito", "Kling", 26),
            Person("Karina", "Goldner", 21),
        )
        Mockito.`when`(personLoaderRepository.load()).thenReturn(data)
        Mockito.`when`(personRepository.insert(any())).thenReturn(true)
        Mockito.`when`(personRepository.getAll()).thenReturn(data)

        setComposeContent()

        composeRule.onNodeWithContentDescription("Searchbar").assertExists()
        composeRule.onNodeWithText("Tito Kling").assertExists()
        composeRule.onNodeWithText("Karina Goldner").assertExists()
    }

    @Test
    fun searchNotExistingPerson_searchbarAndEmptyView_mustVisible() = runBlockingTest {
        val data = arrayListOf(
            Person("Tito", "Kling", 26),
            Person("Karina", "Goldner", 21),
        )
        Mockito.`when`(personLoaderRepository.load()).thenReturn(data)
        Mockito.`when`(personRepository.insert(any())).thenReturn(true)
        Mockito.`when`(personRepository.getAll()).thenReturn(data)
        Mockito.`when`(personRepository.search(any())).thenReturn(emptyList())

        setComposeContent()

        composeRule.onNodeWithContentDescription("SearchTextField").performTextInput("A")
        composeRule.onNodeWithText(context.getString(R.string.empty_message)).assertExists()
        composeRule.onNodeWithText(context.getString(R.string.retry)).assertDoesNotExist()
    }

    @Test
    fun searchExistingPerson_searchbarAndEmptyView_mustVisible() = runBlockingTest {
        val data = arrayListOf(
            Person("Tito", "Kling", 26),
            Person("Karina", "Goldner", 21),
        )
        Mockito.`when`(personLoaderRepository.load()).thenReturn(data)
        Mockito.`when`(personRepository.insert(any())).thenReturn(true)
        Mockito.`when`(personRepository.getAll()).thenReturn(data)
        Mockito.`when`(personRepository.search(any())).thenReturn(
            arrayListOf(Person("Tito", "Kling", 26))
        )

        setComposeContent()

        composeRule.onNodeWithContentDescription("SearchTextField").performTextInput("T")
        composeRule.onNodeWithText("Tito Kling").assertExists()
    }

    private fun setComposeContent() {
        composeRule.setContent {
            AndroidTrieSearchSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PersonsScreen(viewModel)
                }
            }
        }
    }

}