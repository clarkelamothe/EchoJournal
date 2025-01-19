@file:Suppress("OPT_IN_USAGE")

package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.designsystem.RecordingState
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM
import com.clarkelamothe.echojournal.memo.domain.AudioPlayer
import com.clarkelamothe.echojournal.memo.domain.AudioRecorder
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class MemoOverviewViewModel(
    repository: VoiceMemoRepository,
    private val player: AudioPlayer,
    private val recorder: AudioRecorder
) : ViewModel() {
    private val initialTopic = listOf("Work", "Friends", "Family", "Love", "Surprise", "OOP")
    private var filePath: String = ""

    var state by mutableStateOf<MemoOverviewState>(MemoOverviewState.VoiceMemos(topics = initialTopic))
        private set

    private val selectedMoods = MutableStateFlow(emptyList<MoodVM>())
    private val selectedTopics = MutableStateFlow(emptyList<String>())
    private val voiceRecorderState = MutableStateFlow(VoiceRecorderState())
    private val shouldStartTimer = MutableStateFlow(false)
    private val observeAmplitudes = MutableStateFlow(false)
    private val lastEmitted = MutableStateFlow(Duration.ZERO)
    private val amplitudes = MutableStateFlow<List<Int>>(emptyList())

    private val eventChannel = Channel<MemoOverviewEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            repository.getAll(),
            selectedMoods,
            selectedTopics,
            voiceRecorderState,
        ) { memos, selectedMoods, selectedTopics, voiceRecorder ->
            state =
                if (memos.isEmpty()) {
                    MemoOverviewState.Empty(
                        voiceRecorderState = voiceRecorder
                    )
                } else {
                    (state as MemoOverviewState.VoiceMemos).copy(
                        moodChipLabel = moodLabel(selectedMoods),
                        topicChipLabel = topicsLabel(selectedTopics),
                        selectedMood = selectedMoods,
                        selectedTopics = selectedTopics,
                        voiceRecorderState = voiceRecorder,
                        memos = memos.groupBy {
                            it.date
                        }
                    )
                }
        }.launchIn(viewModelScope)

        shouldStartTimer
            .flatMapLatest {
                observeAmplitudes.update { it }
                tickerFlow(
                    start = it,
                    initialDelay = lastEmitted.value
                )
            }
            .onEach {
                lastEmitted.value = it

                voiceRecorderState.update { recorderState ->
                    val init = LocalTime.of(0, 0, 0).plusSeconds(it.inWholeSeconds)
                    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                    recorderState.copy(
                        elapsedTime = init.format(formatter),
                        amplitudes = recorderState.amplitudes + recorder.maxAmp()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun moodLabel(selectedMoods: List<MoodVM>) =
        if (selectedMoods.isEmpty() || selectedMoods.size == MoodVM.entries.size)
            "All Moods"
        else {
            selectedMoods.joinToString(separator = ", ") {
                it.title
            }
        }

    private fun topicsLabel(selectedTopics: List<String>) = if (
        selectedTopics.isEmpty() || selectedTopics.size == initialTopic.size
    ) "All Topics" else {
        with(selectedTopics.take(2)) {
            if (selectedTopics.size < 2) {
                joinToString(", ") { it }
            } else {
                joinToString(", ") {
                    it
                } + " +${initialTopic.size - selectedTopics.size}"
            }
        }
    }

    fun onClearMood() = selectedMoods.update { emptyList() }
    fun onClearTopic() = selectedTopics.update { emptyList() }
    fun onSelect(mood: MoodVM) = selectedMoods.update {
        it.toMutableStateList().apply {
            if (contains(mood)) {
                remove(mood)
            } else {
                add(mood)
                if (size == MoodVM.entries.size) removeAll(this)
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

    fun showBottomSheet(show: Boolean) {
        if (show) startRecording()
        voiceRecorderState.update { VoiceRecorderState(showBottomSheet = show) }
    }

    private fun startRecording() {
        onStartTimer(true)
        voiceRecorderState.update {
            it.copy(
                state = RecordingState.Recording,
                title = RecordingState.Recording.getTitle()
            )
        }
        filePath = recorder.start("memo")
    }

    fun pauseRecording() {
        onStartTimer(false)
        recorder.pause()
        voiceRecorderState.update { voiceRecorderState ->
            with(RecordingState.Paused) {
                voiceRecorderState.copy(
                    state = this,
                    title = getTitle()
                )
            }
        }
    }

    fun stopRecording() {
        onStartTimer(false)
        lastEmitted.update { Duration.ZERO }
        voiceRecorderState.update {
            it.copy(showBottomSheet = false)
        }
        recorder.stop()
    }

    fun finishRecording() {
        onStartTimer(false)
        showBottomSheet(false)
        lastEmitted.update { Duration.ZERO }
        recorder.stop()
        viewModelScope.launch {
            eventChannel.send(MemoOverviewEvent.VoiceMemoRecorded(filePath))
        }
    }

    fun resumeRecording() {
        onStartTimer(true)
        voiceRecorderState.update { voiceRecorderState ->
            with(RecordingState.Recording) {
                voiceRecorderState.copy(
                    state = this,
                    title = getTitle()
                )
            }
        }
        recorder.resume()
    }

    private fun onStartTimer(start: Boolean) {
        shouldStartTimer.update { start }
    }

    private fun tickerFlow(
        start: Boolean,
        period: Duration = 1.seconds,
        initialDelay: Duration = Duration.ZERO
    ) = flow {
        var currentTime = initialDelay
        while (start) {
            emit(currentTime)
            currentTime = currentTime.plus(1.seconds)
            delay(period)
        }
    }
}

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
        override val voiceRecorderState: VoiceRecorderState = VoiceRecorderState()
    ) : MemoOverviewState
}

data class VoiceRecorderState(
    val showBottomSheet: Boolean = false,
    val state: RecordingState = RecordingState.Recording,
    val elapsedTime: String = "0:00:00",
    val title: String = state.getTitle(),
    val amplitudes: List<Float> = emptyList()
)

fun RecordingState.getTitle() = when (this) {
    RecordingState.Recording -> "Recording your memories..."
    RecordingState.Paused -> "Recording paused"
}
