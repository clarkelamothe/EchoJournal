package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AddIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AiIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.EditIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.HashtagIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.PlayIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Gray6
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Secondary70
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Secondary95

@Composable
fun CreateMemoScreenRoot(
    onBackClick: () -> Unit
) {
    CreateMemoScreen(
        onBackClick
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateMemoScreen(
    onAction: () -> Unit
) {
    EchoJournalScaffold(
        withGradient = false,
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.title_create_memo_screen),
                showSettingsButton = false,
                showBackButton = true,
                onBackClick = onAction
            )
        },
        floatingActionButton = {}
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            Mood and Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var title by remember { mutableStateOf("") }

                TextField(
                    singleLine = true,
                    value = title,
                    onValueChange = { text ->
                        title = text
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        IconButton(
                            onClick = {},
                            modifier = Modifier
                                .background(
                                    color = Secondary95,
                                    shape = CircleShape
                                )
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = AddIcon,
                                contentDescription = stringResource(R.string.add_a_mood),
                                tint = Secondary70
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.add_title),
                            color = MaterialTheme.colorScheme.outlineVariant,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.outline
                    ),
                    textStyle = MaterialTheme.typography.headlineLarge
                )
            }

//            Audio and Ai
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {},
                    shape = RoundedCornerShape(99.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.inverseOnSurface // to change later to mood color
                    ),
                    contentPadding = PaddingValues(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                ) {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .shadow(
                                elevation = 8.dp,
                                shape = CircleShape,
                                spotColor = MaterialTheme.colorScheme.primary
                            )
                            .background(
                                color = MaterialTheme.colorScheme.surface,
                                shape = CircleShape
                            )
                            .size(32.dp)
                    ) {
                        Icon(
                            imageVector = PlayIcon,
                            contentDescription = stringResource(R.string.add_a_mood),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        // audio waveform
                    }
                    Text(
                        text = "0:00/12:30",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .shadow(
                            elevation = 2.dp,
                            shape = CircleShape,
                            spotColor = Color.Black
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = AiIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }

//            Topic
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var isFocused by remember {
                    mutableStateOf(false)
                }
                val textFieldState = remember {
                    TextFieldState(initialText = "")
                }
                val topics = remember {
                    mutableStateListOf<String>()
                }

                BasicTextField(
                    lineLimits = TextFieldLineLimits.SingleLine,
                    state = textFieldState,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = if (topics.size < 4) ImeAction.Next else ImeAction.Done
                    ),
                    onKeyboardAction = {
                        if (textFieldState.text.isNotEmpty() && topics.size < 5)
                            topics.add(textFieldState.text.toString())
                        textFieldState.clearText()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    decorator = { innerBox ->
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.Center,
                            maxItemsInEachRow = 5
                        ) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(Alignment.CenterVertically)
                            ) {
                                Icon(
                                    imageVector = HashtagIcon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outlineVariant,
                                )
                            }

                            if (textFieldState.text.isEmpty() && !isFocused) {
                                Text(
                                    text = stringResource(R.string.add_topic),
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            } else {
                                with(topics) {
                                    if (isNotEmpty()) {
                                        mapIndexed { index, topic ->
                                            InputChip(
                                                onClick = {},
                                                label = {
                                                    Text(
                                                        text = topic,
                                                        style = MaterialTheme.typography.labelLarge,
                                                        color = MaterialTheme.colorScheme.secondary,
                                                        overflow = TextOverflow.Ellipsis,
                                                        textAlign = TextAlign.Center
                                                    )
                                                },
                                                selected = true,
                                                avatar = {
                                                    Icon(
                                                        imageVector = HashtagIcon,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.outlineVariant,
                                                    )
                                                },
                                                trailingIcon = {
                                                    Icon(
                                                        imageVector = CloseIcon,
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .size(16.dp)
                                                            .clickable {
                                                                topics.removeAt(index)
                                                            }
                                                    )
                                                },
                                                shape = RoundedCornerShape(16.dp),
                                                colors = InputChipDefaults.inputChipColors(
                                                    containerColor = Gray6,
                                                    selectedContainerColor = Gray6,
                                                    leadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    trailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                                    selectedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                                        alpha = 0.3f
                                                    )
                                                ),
                                                modifier = Modifier.align(Alignment.CenterVertically)
                                            )
                                        }
                                    }
                                }
                                Box(modifier = Modifier.align(Alignment.CenterVertically)) { innerBox() }
                            }
                        }
                    }
                )
            }

//            Description
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var isFocused by remember {
                    mutableStateOf(false)
                }
                val textFieldState = remember {
                    TextFieldState(initialText = "")
                }
                BasicTextField(
                    state = textFieldState,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    decorator = { innerBox ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {},
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = EditIcon,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.outlineVariant
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .weight(1f)
                            ) {
                                if (textFieldState.text.isEmpty() && !isFocused) {
                                    Text(
                                        text = stringResource(R.string.add_description),
                                        color = MaterialTheme.colorScheme.outlineVariant,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                innerBox()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CreateMemoScreenPreview() {
    EchoJournalTheme {
        CreateMemoScreen(
            onAction = {}
        )
    }
}
