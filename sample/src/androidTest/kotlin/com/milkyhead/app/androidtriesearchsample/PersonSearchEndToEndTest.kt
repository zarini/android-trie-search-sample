package com.milkyhead.app.androidtriesearchsample

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.milkyhead.app.androidtriesearchsample.ui.MainActivity
import com.milkyhead.app.androidtriesearchsample.ui.PersonsScreen
import com.milkyhead.app.androidtriesearchsample.ui.theme.AndroidTrieSearchSampleTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@LargeTest
@HiltAndroidTest
class PersonSearchEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        composeRule.setContent {
            AndroidTrieSearchSampleTheme {
                Surface(color = MaterialTheme.colors.background) {
                    PersonsScreen()
                }
            }
        }
    }

    @Test
    fun loadAndShowPerson_searchPerson() {
        composeRule.onNodeWithContentDescription("SearchTextField").performTextInput("a")
        composeRule.onNodeWithText("Alta Kunde").assertExists()
        composeRule.onNodeWithText("Ayana Cassin V").assertExists()
        composeRule.onNodeWithText("Amir Zarini").assertExists()

        composeRule.onNodeWithContentDescription("SearchTextField").performTextClearance()
        composeRule.onNodeWithContentDescription("SearchTextField").performTextInput("blah")
        composeRule.onNodeWithText(context.getString(R.string.empty_message)).assertExists()
        composeRule.onNodeWithText(context.getString(R.string.retry)).assertDoesNotExist()
    }
}