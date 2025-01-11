@file:OptIn(ExperimentalLayoutApi::class)

package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.domain.Mood
import com.clarkelamothe.echojournal.core.presentation.designsystem.DropdownItem
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalChip
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
    onVoiceMemoRecorded: () -> Unit,
) {
    ObserveAsEvents(viewModel.events) {
        when (it) {
            MemoOverviewEvent.VoiceMemoRecorded -> onVoiceMemoRecorded()
        }
    }

    with(viewModel) {
        MemoOverviewScreen(
            state = state,
            onClearMood = ::onClearMood,
            onSelectMood = ::onSelect,
            onClearTopic = ::onClearTopic,
            onSelectTopic = ::onSelect
        )
    }
}


@Composable
fun MemoOverviewScreen(
    state: MemoOverviewState,
    onClearMood: () -> Unit,
    onSelectMood: (Mood) -> Unit,
    onClearTopic: () -> Unit,
    onSelectTopic: (String) -> Unit
) {
    EchoJournalScaffold(
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.your_echojournal),
                showSettingsButton = true,
                onSettingsClick = {
                }
            )
        },
        floatingActionButton = {
            RecordMemoActionButtons(
                modifier = Modifier.padding(bottom = 40.dp),
                onStartRecording = {
                },
                onCancelRecording = {
                },
                onFinishRecording = {
                }
            )
        }
    ) { paddingValues ->
        val menuWidth = LocalConfiguration.current.screenWidthDp.dp - 32.dp

        var isMoodsChipClick by remember { mutableStateOf(false) }
        var isTopicsChipClick by remember { mutableStateOf(false) }

        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            item(contentType = "Filters") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Moods Chip
                    EchoJournalChip(
                        selected = isMoodsChipClick || state.selectedMoods.isNotEmpty(),
                        onClick = {
                            isMoodsChipClick = !isMoodsChipClick
                            isTopicsChipClick = false
                        },
                        label = {
                            Text(
                                text = state.moodChipLabel,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.secondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        },
                        avatar = {
                            MoodIconsRow(
                                icons = state.selectedMoods.map {
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
                        trailingIcon = {
                            if (state.selectedMoods.isNotEmpty() && state.moods.size != state.selectedMoods.size) {
                                Icon(
                                    imageVector = CloseIcon,
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.clickable { onClearMood() }
                                )
                            }
                        }
                    )

                    // Topic Chip
                    EchoJournalChip(
                        selected = isTopicsChipClick || state.selectedTopics.isNotEmpty(),
                        onClick = {
                            isTopicsChipClick = !isTopicsChipClick
                            isMoodsChipClick = false
                        },
                        label = {
                            Text(
                                text = state.topicChipLabel,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.secondary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        },
                        avatar = { },
                        trailingIcon = {
                            if (state.selectedTopics.isNotEmpty() && state.topics.size != state.selectedTopics.size) {
                                Icon(
                                    imageVector = CloseIcon,
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier.clickable { onClearTopic() }
                                )
                            }
                        }
                    )
                }

                // Moods Dropdown
                DropdownMenu(
                    modifier = Modifier.width(menuWidth),
                    expanded = isMoodsChipClick,
                    onDismissRequest = {
                        isMoodsChipClick = false
                        isTopicsChipClick = false
                    },
                    shape = RoundedCornerShape(10.dp),
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    state.moods.forEach { item ->
                        key(item) { item.hashCode() }

                        AnimatedContent(
                            targetState = state.selectedMoods.contains(item),
                            label = "Animate the selected item"
                        ) { isMoodSelected ->
                            DropdownItem(
                                isSelected = isMoodSelected,
                                item = item.title,
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
                                    if (isMoodSelected) {
                                        Icon(
                                            imageVector = CheckIcon,
                                            contentDescription = null
                                        )
                                    }
                                },
                                onSelect = { onSelectMood(item) }
                            )
                        }
                    }
                }

                // Topics Dropdown
                DropdownMenu(
                    modifier = Modifier.width(menuWidth),
                    expanded = isTopicsChipClick,
                    onDismissRequest = {
                        isMoodsChipClick = false
                        isTopicsChipClick = false
                    },
                    shape = RoundedCornerShape(10.dp),
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    state.topics.forEach { topic ->
                        key(topic) { topic.hashCode() }

                        AnimatedContent(
                            targetState = state.selectedTopics.contains(topic),
                            label = "Animate the selected item"
                        ) { isTopicSelected ->
                            DropdownItem(
                                isSelected = isTopicSelected,
                                item = topic,
                                onSelect = { onSelectTopic(topic) },
                                leadingIcon = {
                                    Icon(
                                        tint = Color.Unspecified,
                                        imageVector = HashtagIcon,
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = CheckIcon,
                                        contentDescription = null
                                    )
                                }
                            )
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
            state = MemoOverviewState(),
            onClearMood = {},
            onSelectMood = {},
            onClearTopic = {},
            onSelectTopic = {}
        )
    }
}
