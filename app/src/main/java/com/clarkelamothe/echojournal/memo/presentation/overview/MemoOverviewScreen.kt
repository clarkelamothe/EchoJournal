@file:OptIn(ExperimentalLayoutApi::class)

package com.clarkelamothe.echojournal.memo.presentation.overview

import android.Manifest.permission.RECORD_AUDIO
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarkelamothe.echojournal.R
import com.clarkelamothe.echojournal.core.domain.Mood
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.designsystem.Chip
import com.clarkelamothe.echojournal.core.presentation.designsystem.DropdownItem
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalChip
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalFab
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalScaffold
import com.clarkelamothe.echojournal.core.presentation.designsystem.EchoJournalToolbar
import com.clarkelamothe.echojournal.core.presentation.designsystem.MoodIconsRow
import com.clarkelamothe.echojournal.core.presentation.designsystem.PlayerBar
import com.clarkelamothe.echojournal.core.presentation.designsystem.PlayerState
import com.clarkelamothe.echojournal.core.presentation.designsystem.RecordingBottomSheet
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CheckIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.CloseIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.ExcitedIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.HashtagIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.NeutralIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.PeacefulIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.SadIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.components.icons.StressedIcon
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.EchoJournalTheme
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Neutral25
import com.clarkelamothe.echojournal.core.presentation.designsystem.theme.Neutral95
import com.clarkelamothe.echojournal.core.presentation.ui.ObserveAsEvents
import java.time.LocalDateTime

@Composable
fun MemoOverviewScreenRoot(
    viewModel: MemoOverviewViewModel,
    onSettingsClick: () -> Unit,
    onVoiceMemoRecorded: (filePath: String) -> Unit
) {
    var audioPermissionGranted by remember { mutableStateOf(false) }
    val audioPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        audioPermissionGranted = it
    }

    ObserveAsEvents(viewModel.events) {
        when (it) {
            is MemoOverviewEvent.VoiceMemoRecorded -> {
                onVoiceMemoRecorded(it.filePath)
            }
        }
    }

    with(viewModel) {
        MemoOverviewScreen(
            state = state,
            onClearMood = ::onClearMood,
            onSelectMood = ::onSelect,
            onClearTopic = ::onClearTopic,
            onSelectTopic = ::onSelect,
            onSettingsClick = onSettingsClick,
            onClickFab = {
                if (!audioPermissionGranted) {
                    audioPermissionResultLauncher.launch(RECORD_AUDIO)
                }
                showBottomSheet(audioPermissionGranted)
            }
        )

        if (state.voiceRecorderState.showBottomSheet) {
            RecordingBottomSheet(
                state = state.voiceRecorderState.state,
                title = state.voiceRecorderState.title,
                elapsedTime = state.voiceRecorderState.elapsedTime,
                onDismissRequest = {
                    showBottomSheet(false)
                    stopRecording()
                },
                cancelRecording = ::stopRecording,
                pauseRecording = ::pauseRecording,
                finishRecording = ::finishRecording,
                resumeRecording = ::resumeRecording
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MemoOverviewScreen(
    state: MemoOverviewState,
    onClearMood: () -> Unit,
    onSelectMood: (Mood) -> Unit,
    onClearTopic: () -> Unit,
    onSelectTopic: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onClickFab: () -> Unit
) {
    EchoJournalScaffold(
        topAppBar = {
            EchoJournalToolbar(
                title = stringResource(R.string.your_echojournal),
                showSettingsButton = true,
                onSettingsClick = onSettingsClick
            )
        },
        floatingActionButton = {
            EchoJournalFab(
                modifier = Modifier.padding(bottom = 48.dp),
                onClick = onClickFab
            )
        }
    ) { paddingValues ->
        when (state) {
            is MemoOverviewState.Empty -> EmptyStateScreen(Modifier.fillMaxSize())
            is MemoOverviewState.VoiceMemos -> {
                val menuWidth = LocalConfiguration.current.screenWidthDp.dp - 32.dp

                var isMoodsChipClick by remember { mutableStateOf(false) }
                var isTopicsChipClick by remember { mutableStateOf(false) }

                LazyColumn(
                    modifier = Modifier.padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
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

                    state.memos.map { memo ->
                        item(contentType = "Day") {
                            Spacer(Modifier.height(4.dp))
                            Text(
                                text = memo.key.toString(),
                                modifier = Modifier.padding(top = 12.dp),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        items(items = memo.value, key = { it.id }) {
                            Row(
                                modifier = Modifier.fillParentMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = NeutralIcon,
                                    contentDescription = null,
                                    tint = Color.Unspecified
                                )

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .shadow(
                                            elevation = 8.dp,
                                            shape = RoundedCornerShape(10.dp),
                                            spotColor = MaterialTheme.colorScheme.primary
                                        )
                                        .background(MaterialTheme.colorScheme.surface)
                                        .padding(horizontal = 14.dp, vertical = 12.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "My Entry",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "17:30",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }

                                    PlayerBar(
                                        modifier = Modifier,
                                        playerState = PlayerState.Idle,
                                        timeStamp = "0:00/7:30",
                                        containerColor = Neutral25,
                                        iconColor = Neutral95,
                                        progress = 0.7f,
                                        onClickPlay = {},
                                        onClickPause = {}
                                    )

                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        style = MaterialTheme.typography.bodyMedium,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 3,
                                        text = it.description
                                    )

                                    FlowRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        it.topics.map { topic ->
                                            Chip(
                                                modifier = Modifier,
                                                text = topic,
                                                selected = true,
                                                onClick = {
                                                    onSelectTopic(topic)
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
        }
    }
}

@Preview
@Composable
private fun MemoOverviewScreenPreview() {
    EchoJournalTheme {
        MemoOverviewScreen(
            state = MemoOverviewState.VoiceMemos(
                moodChipLabel = "All Moods",
                topicChipLabel = "Sad, Excited +2",
                moods = listOf(Mood.Neutral, Mood.Sad, Mood.Peaceful),
                selectedMoods = listOf(Mood.Sad, Mood.Excited),
                topics = listOf("Work", "Family", "Life", "Love", "Surprise"),
                selectedTopics = listOf("Work", "Family", "Life"),
                memos = mapOf(
                    LocalDateTime.now() to listOf(
                        VoiceMemo(
                            id = 0,
                            title = "My Entry",
                            dateTime = LocalDateTime.now(),
                            description = "If a voice memo’s play button is pressed, possible playback for other memos should stop and the new memo should start playing.",
                            audio = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc id venenatis justo, vel tristique magna. Donec id lectus sit amet tortor tempor porttitor. Aenean egestas lectus id lectus varius, sit amet laoreet justo tempus. Sed varius mauris nunc, non porta enim finibus pellentesque. Maecenas vitae massa ac nibh porttitor ultricies eget vel enim.",
                            mood = Mood.Peaceful,
                            topics = listOf("Work", "Life")
                        )
                    )
                )
            ),
            onClearMood = {},
            onSelectMood = {},
            onClearTopic = {},
            onSelectTopic = {},
            onSettingsClick = {},
            onClickFab = {}
        )
    }
}
