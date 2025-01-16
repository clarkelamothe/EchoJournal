package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.presentation.designsystem.Chip
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.PlayerBar
import com.clarkelamothe.echojournal.core.presentation.designsystem.PlayerState
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AddIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.AiIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CheckIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.EditIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.HashtagIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.ButtonGradient
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Secondary70
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Secondary95
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.SurfaceTint
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.SurfaceVariant
import com.clarkelamothe.echojournal.core.presentation.ui.ObserveAsEvents
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

@Composable
fun CreateMemoScreenRoot(
    viewModel: CreateMemoViewModel,
    onBackClick: () -> Unit
) {
    ObserveAsEvents(viewModel.events) {
        when (it) {
            else -> {}
        }
    }

    CreateMemoScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                CreateMemoAction.OnBackClick -> onBackClick()
                CreateMemoAction.OnCancelClick -> onBackClick()
                else -> viewModel.onAction(it)
            }
        }
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CreateMemoScreen(
    state: CreateMemoState,
    onAction: (CreateMemoAction) -> Unit
) {
    EchoJournalScaffold(
        withGradient = false,
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.title_create_memo_screen),
                showSettingsButton = false,
                showBackButton = true,
                onBackClick = {
                    onAction(CreateMemoAction.OnBackClick)
                }
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
                TextField(
                    singleLine = true,
                    value = state.memoTitle,
                    onValueChange = {
                        onAction(CreateMemoAction.OnTitleChange(it))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon = {
                        IconButton(
                            onClick = {
                                onAction(CreateMemoAction.OnAddMoodClick)
                            },
                            modifier = Modifier
                                .background(
                                    color = Secondary95,
                                    shape = CircleShape
                                )
                                .size(32.dp)
                        ) {
                            Icon(
                                imageVector = state.mood?.let {
                                    ImageVector.vectorResource(it.icon)
                                } ?: AddIcon,
                                contentDescription = stringResource(R.string.add_a_mood),
                                tint = state.mood?.let { Color.Unspecified } ?: Secondary70
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
                PlayerBar(
                    modifier = Modifier.weight(1f),
                    playerState = PlayerState.Idle,
                    containerColor = state.mood?.color25
                        ?: MaterialTheme.colorScheme.inverseOnSurface,
                    iconColor = state.mood?.color80 ?: MaterialTheme.colorScheme.primary,
                    timeStamp = "7:00/12:30",
                    progress = 2.3f,
                    onClickPlay = {},
                    onClickPause = {}
                )

                IconButton(
                    onClick = {
                        onAction(CreateMemoAction.OnAiClick)
                    },
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
                        tint = state.mood?.color80 ?: MaterialTheme.colorScheme.primary,
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

                BasicTextField(
                    lineLimits = TextFieldLineLimits.SingleLine,
                    state = textFieldState,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    cursorBrush = SolidColor(MaterialTheme.colorScheme.onBackground),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    onKeyboardAction = {
                        if (textFieldState.text.isNotEmpty())
                            onAction(CreateMemoAction.OnAddTopic(textFieldState.text.toString()))
                        textFieldState.clearText()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    decorator = { innerBox ->
                        FlowRow(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.Center
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

                            if (textFieldState.text.isEmpty() && !isFocused && state.topics.isEmpty()) {
                                Text(
                                    text = stringResource(R.string.add_topic),
                                    color = MaterialTheme.colorScheme.outlineVariant,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            } else {
                                with(state.topics) {
                                    if (isNotEmpty()) {
                                        mapIndexed { index, topic ->
                                            Chip(
                                                modifier = Modifier.align(Alignment.CenterVertically),
                                                text = topic,
                                                selected = true,
                                                trailingIcon = {
                                                    IconButton(
                                                        onClick = {
                                                            onAction(
                                                                CreateMemoAction.OnRemoveTopic(
                                                                    index
                                                                )
                                                            )
                                                        },
                                                        modifier = Modifier
                                                            .size(16.dp)
                                                            .align(Alignment.CenterVertically)
                                                    ) {
                                                        Icon(
                                                            imageVector = CloseIcon,
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colorScheme.outlineVariant,
                                                        )
                                                    }
                                                }
                                            )
                                        }
                                    }
                                    Box(modifier = Modifier.align(Alignment.CenterVertically)) { innerBox() }
                                }
                            }
                        }
                    }
                )
            }

//            Description
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.Top
            ) {
                var isFocused by remember {
                    mutableStateOf(false)
                }

                val textFieldState by remember {
                    mutableStateOf(TextFieldState(state.description))
                }

                BasicTextField(
                    state = textFieldState,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    lineLimits = TextFieldLineLimits.MultiLine(),
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
                            verticalAlignment = Alignment.Top
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
                                if (state.description.isEmpty() && !isFocused) {
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

//            Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Button(
                    onClick = {
                        onAction(CreateMemoAction.OnCancelClick)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceTint.copy(alpha = 0.12f),
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Cancel"
                    )
                }

                Button(
                    enabled = state.canSave,
                    modifier = Modifier
                        .weight(1f)
                        .shadow(
                            elevation = 8.dp,
                            shape = ButtonDefaults.shape,
                            spotColor = MaterialTheme.colorScheme.primary
                        )
                        .then(
                            if (state.canSave) Modifier.background(
                                brush = ButtonGradient,
                                shape = ButtonDefaults.shape
                            ) else Modifier.background(
                                color = SurfaceVariant,
                                shape = ButtonDefaults.shape
                            )
                        ),
                    onClick = {
                        onAction(CreateMemoAction.OnSaveClick)
                    },
                    colors = ButtonColors(
                        disabledContainerColor = SurfaceVariant,
                        containerColor = Color.Unspecified,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.outline
                    ),
                ) {
                    Text(
                        text = "Save"
                    )
                }
            }
            Spacer(Modifier.height(20.dp))

//            BottomSheet
            if (state.showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = {
                        onAction(CreateMemoAction.DismissBottomSheet)
                    },
                    sheetState = rememberModalBottomSheetState(),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                    containerColor = MaterialTheme.colorScheme.background,
                    tonalElevation = 16.dp,
                    dragHandle = {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .width(32.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(100.dp))
                                .background(MaterialTheme.colorScheme.outlineVariant)
                        )
                    }
                ) {
                    Text(
                        text = "How Are you doing?",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(Modifier.height(32.dp))

                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MoodVM.entries.map {
                            Column(
                                modifier = Modifier.size(64.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                IconButton(
                                    onClick = {
                                        onAction(CreateMemoAction.OnSelectMood(it))
                                    },
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    if (it == state.mood) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(it.icon),
                                            contentDescription = null,
                                            tint = Color.Unspecified
                                        )
                                    } else {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(it.iconOutlined),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.outlineVariant
                                        )
                                    }
                                }
                                Text(
                                    text = it.title,
                                    textAlign = TextAlign.Center,
                                    color = state.mood?.let {
                                        MaterialTheme.colorScheme.onSurface
                                    } ?: MaterialTheme.colorScheme.outline,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(24.dp))

                    // Button for bottomsheet
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                onAction(CreateMemoAction.OnCancelMoodClick)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SurfaceTint.copy(alpha = 0.12f),
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                text = "Cancel"
                            )
                        }

                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .shadow(
                                    elevation = 8.dp,
                                    shape = CircleShape,
                                    spotColor = MaterialTheme.colorScheme.primary
                                )
                                .background(
                                    brush = ButtonGradient,
                                    shape = CircleShape
                                ),
                            onClick = {
                                onAction(CreateMemoAction.OnConfirmMoodClick)
                            },
                            colors = ButtonColors(
                                disabledContainerColor = SurfaceVariant,
                                containerColor = Color.Unspecified,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                disabledContentColor = MaterialTheme.colorScheme.outline
                            ),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = CheckIcon,
                                    contentDescription = null
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    text = "Confirm"
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun CreateMemoScreenPreview() {
    EchoJournalTheme {
        CreateMemoScreen(
            state = CreateMemoState(),
            onAction = {}
        )
    }
}
