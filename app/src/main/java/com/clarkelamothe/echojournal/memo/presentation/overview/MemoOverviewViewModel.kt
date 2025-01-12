package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.core.domain.Mood
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.designsystem.PlayerState
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime

class MemoOverviewViewModel(
    repository: VoiceMemoRepository
) : ViewModel() {
    private val initialTopic = listOf("Work", "Friends", "Family", "Love", "Surprise")

    var state by mutableStateOf<MemoOverviewState>(MemoOverviewState.VoiceMemos(topics = initialTopic))
        private set

    private val selectedMoods = MutableStateFlow(emptyList<Mood>())
    private val selectedTopics = MutableStateFlow(emptyList<String>())
    private val showBottomSheet = MutableStateFlow(false)

    private val eventChannel = Channel<MemoOverviewEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            repository.getAll(),
            selectedMoods,
            selectedTopics,
            showBottomSheet,
        ) { memos, selectedMoods, selectedTopics, showBottomSheet ->
            state =
                if (memos.isEmpty()) {
                    MemoOverviewState.Empty(
                        showBottomSheet,
                        bottomSheetTitle = "Recording your memories...",
                        bottomSheetTime = "01:30:45",
                        bottomSheetState = PlayerState.Idle,
                    )
                } else {
                    (state as MemoOverviewState.VoiceMemos).copy(
                        moodChipLabel = moodLabel(),
                        topicChipLabel = topicsLabel(),
                        selectedMoods = selectedMoods,
                        selectedTopics = selectedTopics,
                        showBottomSheet = showBottomSheet
                    )
                }
        }.launchIn(viewModelScope)
    }

    private fun moodLabel() =
        if (selectedMoods.value.isEmpty() || selectedMoods.value.size == Mood.entries.size)
            "All Moods"
        else {
            selectedMoods.value.joinToString(separator = ", ") {
                it.title
            }
        }

    private fun topicsLabel() = if (
        selectedTopics.value.isEmpty() || selectedTopics.value.size == initialTopic.size
    ) "All Topics" else {
        with(selectedTopics.value.take(2)) {
            if (selectedTopics.value.size < 2) {
                joinToString(", ") { it }
            } else {
                joinToString(", ") {
                    it
                } + " +${initialTopic.size - selectedTopics.value.size}"
            }
        }
    }

    fun onClearMood() = selectedMoods.update { emptyList() }
    fun onClearTopic() = selectedTopics.update { emptyList() }
    fun onSelect(mood: Mood) = selectedMoods.update {
        it.toMutableStateList().apply {
            if (contains(mood)) {
                remove(mood)
            } else {
                add(mood)
                if (size == Mood.entries.size) removeAll(this)
            }
        }
    }

    fun onSelect(topic: String) = selectedTopics.update {
        it.toMutableStateList().apply {
            if (contains(topic)) {
                remove(topic)
            } else {
                add(topic)
                sort()
                if (size == initialTopic.size) removeAll(this)
            }
        }
    }

    fun showBottomSheet(show: Boolean) = showBottomSheet.update { show }
}

sealed interface MemoOverviewState {
    val showBottomSheet: Boolean
    val bottomSheetTitle: String
    val bottomSheetTime: String
    val bottomSheetState: PlayerState

    data class Empty(
        override val showBottomSheet: Boolean,
        override val bottomSheetTitle: String,
        override val bottomSheetTime: String,
        override val bottomSheetState: PlayerState
    ) : MemoOverviewState

    data class VoiceMemos(
        val moodChipLabel: String = "",
        val topicChipLabel: String = "",
        val moods: List<Mood> = Mood.entries,
        val selectedMoods: List<Mood> = emptyList(),
        val topics: List<String> = listOf(""),
        val selectedTopics: List<String> = emptyList(),
        val memos: Map<LocalDateTime, List<VoiceMemo>> = emptyMap(),
        override val showBottomSheet: Boolean = false,
        override val bottomSheetTitle: String = "Recording your memories...",
        override val bottomSheetTime: String = "01:30:46",
        override val bottomSheetState: PlayerState = PlayerState.Idle,
    ) : MemoOverviewState
}
