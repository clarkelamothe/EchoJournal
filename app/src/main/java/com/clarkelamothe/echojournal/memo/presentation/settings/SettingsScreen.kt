package com.clarkelamothe.echojournal.memo.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.MoodsRow
import com.clarkelamothe.echojournal.core.presentation.designsystem.TopicSelectionAlternate
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AddIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Gray6
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

@Composable
fun SettingsScreenRoot(
    viewModel: SettingsViewModel,
    onBackClick: () -> Unit
) {
    val state = viewModel.state

    SettingsScreen(
        onAction = {
            when (it) {
                SettingsScreenAction.OnBackClick -> onBackClick()
                else -> viewModel.onAction(it)
            }
        },
        initialSettings = state.settings,
        dropdownExpanded = state.dropdownExpanded,
        topicSuggestion = state.suggestions,
        inputText = state.inputText
    )
}

@Composable
fun SettingsScreen(
    initialSettings: SettingsVM = SettingsVM(),
    dropdownExpanded: Boolean = false,
    topicSuggestion: List<String> = emptyList(),
    inputText: String = "",
    onAction: (SettingsScreenAction) -> Unit
) {
    EchoJournalScaffold(
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.settings),
                showBackButton = true,
                onBackClick = {
                    onAction(SettingsScreenAction.OnBackClick)
                }
            )
        },
        floatingActionButton = {}
    ) { paddingValues ->
        val width = LocalConfiguration.current.screenWidthDp.dp - 32.dp

        var isFocused by remember {
            mutableStateOf(false)
        }

        val topicInput = remember {
            TextFieldState(initialText = inputText)
        }

        LaunchedEffect(topicInput.text) {
            onAction(SettingsScreenAction.OnInputTopic(topicInput.text))
        }

        var addClick by remember {
            mutableStateOf(false)
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(addClick) {
            if (addClick) {
                focusRequester.requestFocus()
                keyboardController?.show()
            }
        }

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = "My Mood",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Select default mood to apply to all new entries",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(14.dp))
                MoodsRow(
                    modifier = Modifier.fillMaxWidth(),
                    selectedMood = initialSettings.moodVM,
                    onSelectMood = { mood ->
                        onAction(SettingsScreenAction.OnMoodSelect(mood))
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(14.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "My Topics",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Select default topics to apply to all new entries",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(14.dp))

                BasicTextField(
                    lineLimits = TextFieldLineLimits.SingleLine,
                    state = topicInput,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    onKeyboardAction = {
                        if (topicInput.text.isNotEmpty())
                            onAction(SettingsScreenAction.OnAddTopic(topicInput.text.toString()))
                        topicInput.clearText()
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    decorator = { innerBox ->
                        TopicSelectionAlternate(
                            leadingIcon = {
                                IconButton(
                                    onClick = {
                                        isFocused = true
                                        addClick = !addClick
                                    },
                                    modifier = Modifier
                                        .background(Gray6, CircleShape)
                                        .size(32.dp).padding(4.dp)
                                ) {
                                    Icon(
                                        imageVector = AddIcon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            },
                            suggestions = topicSuggestion,
                            dropdownWidth = width,
                            textFieldState = topicInput,
                            initialTopics = initialSettings.topics,
                            innerBox = innerBox,
                            isDropdownExpanded = dropdownExpanded,
                            onDismissRequest = {
                                onAction(SettingsScreenAction.DismissDropdown)
                            },
                            onRemoveTopic = {
                                onAction(SettingsScreenAction.OnRemoveTopic(it))
                            },
                            onAddTopic = {
                                isFocused = !isFocused
                                onAction(SettingsScreenAction.OnAddTopic(it))
                            },
                            isFocused = isFocused
                        )
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    EchoJournalTheme {
        SettingsScreen(
            onAction = {},
            initialSettings = SettingsVM(
                moodVM = MoodVM.Neutral,
                topics = listOf("Work", "Conundrums")
            )
        )
    }
}
