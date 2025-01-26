package com.clarkelamothe.echojournal.memo.presentation.overview

import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

sealed interface MemoOverviewState {
    val voiceRecorderState: VoiceRecorderState

    data class Empty(
        override val voiceRecorderState: VoiceRecorderState = VoiceRecorderState()
    ) : MemoOverviewState

    data class VoiceMemos(
        val moodChipLabel: String = "",
        val topicChipLabel: String = "",
        val moods: List<MoodVM> = MoodVM.entries,
        val selectedMood: List<MoodVM> = emptyList(),
        val topics: List<String> = listOf(""),
        val selectedTopics: List<String> = emptyList(),
        val memos: Map<String, List<VoiceMemo>> = emptyMap(),
        val descriptionMaxLine: Int = 3,
        val currentPlayingAudio: CurrentPlayingAudio = CurrentPlayingAudio(),
        override val voiceRecorderState: VoiceRecorderState = VoiceRecorderState(),
    ) : MemoOverviewState
}
