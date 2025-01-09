package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.domain.Mood
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.MoodIconsRow
import com.clarkelamothe.echojournal.core.presentation.designsystem.RecordMemoActionButtons
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CheckIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.ExcitedIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.HashtagIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.NeutralIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.PeacefulIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.SadIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.StressedIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme
import com.clarkelamothe.echojournal.core.presentation.ui.ObserveAsEvents

@Composable
fun MemoOverviewScreenRoot(
    viewModel: MemoOverviewViewModel,
    onSettingsClick: () -> Unit,
    onVoiceMemoRecorded: () -> Unit
) {
    ObserveAsEvents(viewModel.events) {
        when (it) {
            MemoOverviewEvent.VoiceMemoRecorded -> onVoiceMemoRecorded()
        }
    }

    MemoOverviewScreen(
        onAction = {
            when (it) {
                MemoOverviewScreenAction.OnSettingsClick -> onSettingsClick()
                MemoOverviewScreenAction.OnCancelRecording -> {

                }

                MemoOverviewScreenAction.OnStartRecording -> {

                }

                else -> viewModel.onAction(it)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MemoOverviewScreen(
    onAction: (MemoOverviewScreenAction) -> Unit
) {
    EchoJournalScaffold(
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.your_echojournal),
                showSettingsButton = true,
                onSettingsClick = {
                    onAction(MemoOverviewScreenAction.OnSettingsClick)
                }
            )
        },
        floatingActionButton = {
            RecordMemoActionButtons(
                modifier = Modifier.padding(bottom = 40.dp),
                onStartRecording = {
                    onAction(MemoOverviewScreenAction.OnStartRecording)
                },
                onCancelRecording = {
                    onAction(MemoOverviewScreenAction.OnCancelRecording)
                },
                onFinishRecording = {
                    onAction(MemoOverviewScreenAction.OnFinishRecording)
                }
            )
        }
    ) { paddingValues ->
        var isMoodsSelected by remember { mutableStateOf(false) }
        var isTopicsSelected by remember { mutableStateOf(false) }

        val selectedMoods = remember { mutableStateListOf<Mood>() }
        val initialTopics = remember {
            mutableStateListOf<String>().apply {
                addAll(
                    listOf("Work", "Friends", "Family", "Love", "Surprise")
                )
            }
        }
        val selectedTopics = remember { mutableStateListOf<String>() }

        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item(contentType = "Filters") {
                ExposedDropdownMenuBox(
                    expanded = true,
                    onExpandedChange = {
                    }
                ) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryEditable),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Moods Chip
                        InputChip(
                            selected = isMoodsSelected,
                            onClick = {
                                isMoodsSelected = !isMoodsSelected
                                isTopicsSelected = false
                            },
                            label = {
                                Text(
                                    text = if (selectedMoods.isEmpty() || selectedMoods.size == Mood.entries.size)
                                        "All Moods"
                                    else {
                                        selectedMoods.joinToString(separator = ", ") {
                                            it.title
                                        }
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.secondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                            },
                            avatar = {
                                MoodIconsRow(
                                    icons = selectedMoods.map {
                                        when (it) {
                                            Mood.Stressed -> StressedIcon
                                            Mood.Sad -> SadIcon
                                            Mood.Neutral -> NeutralIcon
                                            Mood.Peaceful -> PeacefulIcon
                                            Mood.Excited -> ExcitedIcon
                                        }
                                    }
                                )
                            },
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isMoodsSelected)
                                    MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.outlineVariant
                            ),
                            trailingIcon = {
                                if (selectedMoods.isNotEmpty() && Mood.entries.size != selectedMoods.size) {
                                    Icon(
                                        imageVector = CloseIcon,
                                        contentDescription = null,
                                        tint = Color.Unspecified,
                                        modifier = Modifier
                                            .clickable {
                                                selectedMoods.clear()
                                            }
                                    )
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = InputChipDefaults.inputChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = Color.Transparent,
                                selectedLeadingIconColor = Color.Unspecified,
                                selectedTrailingIconColor = Color.Unspecified
                            )
                        )


                        // Topic Chip
                        InputChip(
                            selected = isTopicsSelected,
                            onClick = {
                                isTopicsSelected = !isTopicsSelected
                                isMoodsSelected = false
                            },
                            label = {
                                Text(
                                    text = if (
                                        selectedTopics.isEmpty() || selectedTopics.size == initialTopics.size
                                    ) "All Topics" else {
                                        val firstTwo = selectedTopics.take(2)

                                        if (selectedTopics.size < 2) {
                                            firstTwo.joinToString(", ") {
                                                it
                                            }
                                        } else {
                                            firstTwo.joinToString(", ") {
                                                it
                                            } + " +${initialTopics.size - selectedTopics.size}"
                                        }
                                    },
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.secondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                            },
                            avatar = {},
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isTopicsSelected)
                                    MaterialTheme.colorScheme.primaryContainer
                                else MaterialTheme.colorScheme.outlineVariant
                            ),
                            trailingIcon = {
                                if (selectedTopics.isNotEmpty() && initialTopics.size != selectedTopics.size) {
                                    Icon(
                                        imageVector = CloseIcon,
                                        contentDescription = null,
                                        tint = Color.Unspecified,
                                        modifier = Modifier
                                            .clickable {
                                                selectedTopics.clear()
                                            }
                                    )
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = InputChipDefaults.inputChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = Color.Transparent,
                                selectedLeadingIconColor = Color.Unspecified,
                                selectedTrailingIconColor = Color.Unspecified
                            )
                        )
                    }

                    // Moods Dropdown
                    ExposedDropdownMenu(
                        expanded = isMoodsSelected,
                        onDismissRequest = {
                            isMoodsSelected = false
                            isTopicsSelected = false
                        },
                        shape = RoundedCornerShape(10.dp),
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        Mood.entries.forEach { item ->
                            key(item) { item.hashCode() }

                            AnimatedContent(
                                targetState = selectedMoods.contains(item),
                                label = "Animate the selected item"
                            ) { isSelected ->
                                DropdownMenuItem(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(
                                            0.dp,
                                            Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected)
                                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                            else Color.Transparent
                                        ),
                                    colors = MenuItemColors(
                                        textColor = MaterialTheme.colorScheme.secondary,
                                        leadingIconColor = Color.Unspecified,
                                        trailingIconColor = Color.Unspecified,
                                        disabledTextColor = Color.Unspecified,
                                        disabledLeadingIconColor = Color.Unspecified,
                                        disabledTrailingIconColor = Color.Unspecified
                                    ),
                                    text = {
                                        Text(
                                            text = item.title
                                        )
                                    },
                                    onClick = {
                                        selectedMoods.apply {
                                            if (contains(item)) {
                                                remove(item)
                                            } else {
                                                add(item)
                                                if (selectedMoods.size == Mood.entries.size) {
                                                    removeAll(selectedMoods)
                                                }
                                            }
                                        }
                                    },
                                    leadingIcon = {
                                        Icon(
                                            tint = Color.Unspecified,
                                            imageVector = when (item) {
                                                Mood.Stressed -> StressedIcon
                                                Mood.Sad -> SadIcon
                                                Mood.Neutral -> NeutralIcon
                                                Mood.Peaceful -> PeacefulIcon
                                                Mood.Excited -> ExcitedIcon
                                            },
                                            contentDescription = null
                                        )
                                    },
                                    trailingIcon = {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = CheckIcon,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }

                    // Topics Dropdown
                    ExposedDropdownMenu(
                        expanded = isTopicsSelected,
                        onDismissRequest = {
                            isMoodsSelected = false
                            isTopicsSelected = false
                        },
                        shape = RoundedCornerShape(10.dp),
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        initialTopics.forEach { topic ->
                            key(topic) { topic.hashCode() }

                            AnimatedContent(
                                targetState = selectedTopics.contains(topic),
                                label = "Animate the selected item"
                            ) { isSelected ->
                                DropdownMenuItem(
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .border(
                                            0.dp,
                                            Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (isSelected)
                                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                            else Color.Transparent
                                        ),
                                    colors = MenuItemColors(
                                        textColor = MaterialTheme.colorScheme.secondary,
                                        leadingIconColor = Color.Unspecified,
                                        trailingIconColor = Color.Unspecified,
                                        disabledTextColor = Color.Unspecified,
                                        disabledLeadingIconColor = Color.Unspecified,
                                        disabledTrailingIconColor = Color.Unspecified
                                    ),
                                    text = {
                                        Text(
                                            text = topic
                                        )
                                    },
                                    onClick = {
                                        selectedTopics.apply {
                                            if (contains(topic)) {
                                                remove(topic)
                                            } else {
                                                add(topic)
                                                if (selectedTopics.size == initialTopics.size) {
                                                    removeAll(selectedTopics)
                                                }
                                            }
                                        }
                                    },
                                    leadingIcon = {
                                        Icon(
                                            tint = Color.Unspecified,
                                            imageVector = HashtagIcon,
                                            contentDescription = null
                                        )
                                    },
                                    trailingIcon = {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = CheckIcon,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MemoOverviewScreenPreview() {
    EchoJournalTheme {
        MemoOverviewScreen(
            onAction = {}
        )
    }
}
