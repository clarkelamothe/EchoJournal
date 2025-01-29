package com.clarkelamothe.echojournal.memo.presentation.overview

import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

data class MemoOverviewState(
    val showEmptyState: Boolean = false,
    val moodChipLabel: String = "",
    val topicChipLabel: String = "",
    val moods: List<MoodVM> = MoodVM.entries,
    val selectedMood: List<MoodVM> = emptyList(),
    val topics: List<String> = listOf(""),
    val selectedTopics: List<String> = emptyList(),
    val memos: Map<String, List<VoiceMemo>> = emptyMap(),
    val descriptionMaxLine: Int = 3,
    val currentPlayingAudio: CurrentPlayingAudio = CurrentPlayingAudio(),
    val voiceRecorderState: VoiceRecorderState = VoiceRecorderState(),
)
