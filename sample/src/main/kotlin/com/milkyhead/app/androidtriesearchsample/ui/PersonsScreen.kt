package com.milkyhead.app.androidtriesearchsample.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.milkyhead.app.androidtriesearchsample.R
import com.milkyhead.app.androidtriesearchsample.domain.model.Person
import com.milkyhead.app.androidtriesearchsample.ui.event.PersonsEvent
import com.milkyhead.app.androidtriesearchsample.ui.state.PersonsState
import com.milkyhead.app.androidtriesearchsample.ui.theme.DividerColor
import com.milkyhead.app.androidtriesearchsample.ui.theme.Purple200
import com.milkyhead.app.androidtriesearchsample.ui.theme.Purple500


@Composable
fun PersonsScreen(
    viewModel: PersonsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    DisposableEffect(key1 = viewModel) {
        viewModel.onStart()
        onDispose { viewModel.onStop() }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarItem(
            onTextChanged = { textFiledValue ->
                /*
                 * This is not optimize that create event per user input,
                 * it is better user typing complete then create event with final query,
                 * but in test project not matter
                 */
                viewModel.event(PersonsEvent.Search(textFiledValue.text))
            }
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )

        when {
            state.loading -> {
                LoadingView()
            }
            state.emptyView -> {
                EmptyView(state.retry) {
                    viewModel.event(PersonsEvent.LoadPersons)
                }
            }
            else -> {
                PersonListView(state) {
                    viewModel.event(PersonsEvent.SelectPerson(it))
                }
            }
        }
    }
}

@Composable
private fun SearchBarItem(
    modifier: Modifier = Modifier,
    onTextChanged: (TextFieldValue) -> Unit
) {
    var input by remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEDF0F8)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search),
            tint = Color(0xFF9298AA),
            modifier = Modifier.padding(start = 8.dp)
        )

        TextField(
            value = input,
            placeholder = { Text(text = stringResource(id = R.string.search)) },
            onValueChange = {
                input = it
                onTextChanged(input)
            },
            modifier = Modifier
                .fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                disabledTextColor = Color.Transparent,
                backgroundColor = Color(0xFFEDF0F8),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyView(
    retry: Boolean,
    onRetryClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.empty_message)
        )

        if (retry) {
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onRetryClicked) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Composable
private fun PersonListView(
    personsState: PersonsState,
    onPersonClicked: (Person) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(personsState.persons) {
            PersonItem(person = it, onPersonClicked = onPersonClicked)
            Divider(
                color = DividerColor,
                thickness = 2.dp
            )
        }
    }
}

@Composable
private fun PersonItem(
    person: Person,
    modifier: Modifier = Modifier,
    onPersonClicked: (Person) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .requiredHeight(64.dp)
            .clickable { onPersonClicked(person) },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Box(
            modifier = Modifier
                .size(56.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .background(Purple200),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${person.name[0].uppercase()}${person.family[0].uppercase()}",
                textAlign = TextAlign.Center,
                color = Purple500,
                maxLines = 1,
                fontSize = 20.sp
            )
        }

        Text(
            text = "${person.name} ${person.family}",
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        )
    }
}
