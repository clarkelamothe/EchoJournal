@file:Suppress("OPT_IN_USAGE")

package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.designsystem.RecordingState
import com.clarkelamothe.echojournal.core.presentation.ui.mappers.toVM
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM
import com.clarkelamothe.echojournal.memo.domain.AudioPlayer
import com.clarkelamothe.echojournal.memo.domain.AudioRecorder
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import com.clarkelamothe.echojournal.memo.presentation.formatDate
import com.clarkelamothe.echojournal.memo.presentation.formatDuration
import com.clarkelamothe.echojournal.memo.presentation.formatTime
import com.clarkelamothe.echojournal.memo.presentation.toElapsedTimeFormatted
import com.clarkelamothe.echojournal.memo.presentation.toLocalDate
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
import java.time.LocalDate
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class MemoOverviewViewModel(
    repository: VoiceMemoRepository,
    private val player: AudioPlayer,
    private val recorder: AudioRecorder
) : ViewModel() {
    private var filePath: String = ""

    var state by mutableStateOf<MemoOverviewState>(MemoOverviewState.VoiceMemos())
        private set

    private val filterState = MutableStateFlow(FilterState())

    private val voiceRecorderState = MutableStateFlow(VoiceRecorderState())
    private val shouldStartTimer = MutableStateFlow(false)
    private val observeAmplitudes = MutableStateFlow(false)
    private val lastEmitted = MutableStateFlow(Duration.ZERO)
    private val descriptionMaxLines = MutableStateFlow(3)
    private val amplitudes = MutableStateFlow<List<Int>>(emptyList())

    private val eventChannel = Channel<MemoOverviewEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            repository.getAll(),
            repository.getAllTopics(),
            filterState,
            voiceRecorderState,
            descriptionMaxLines
        ) { memos, initialTopics, filterState, voiceRecorder, maxLines ->
            state =
                if (memos.isEmpty()) {
                    MemoOverviewState.Empty(
                        voiceRecorderState = voiceRecorder
                    )
                } else {
                    val selectedTopics = filterState.selectedTopics
                    val selectedMoods = filterState.selectedMoods

                    val voiceMemos = filteredVoiceMemos(selectedMoods, selectedTopics, memos)

                    (state as MemoOverviewState.VoiceMemos).copy(
                        moodChipLabel = filterState.moodLabel(selectedMoods),
                        topicChipLabel = filterState.topicsLabel(selectedTopics, initialTopics),
                        selectedMood = selectedMoods,
                        selectedTopics = selectedTopics,
                        voiceRecorderState = voiceRecorder,
                        memos = voiceMemos
                            .map {
                                player.init(it.filePath)
                                it.copy(
                                    date = it.date.formatDate(),
                                    time = it.time.formatTime(),
                                    duration = player.duration().formatDuration()
                                )
                            }.groupBy {
                                it.date
                            }.toSortedMap(
                                compareByDescending {
                                    when (it) {
                                        "Today" -> LocalDate.now()
                                        "Yesterday" -> LocalDate.now().minusDays(1)
                                        else -> try {
                                            it.toLocalDate()
                                        } catch (e: Exception) {
                                            LocalDate.MIN
                                        }
                                    }
                                }
                            ),
                        topics = initialTopics,
                        descriptionMaxLine = maxLines
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
                    recorderState.copy(
                        elapsedTime = it.toElapsedTimeFormatted(),
                        amplitudes = recorderState.amplitudes + recorder.maxAmp()
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun filteredVoiceMemos(
        selectedMoods: List<MoodVM>,
        selectedTopics: List<String>,
        memos: List<VoiceMemo>
    ) = when {
        selectedMoods.isEmpty() && selectedTopics.isEmpty() -> memos
        selectedMoods.isEmpty() -> memos.filter { memo ->
            memo.topics.isNotEmpty() && memo.topics.any { it in selectedTopics }
        }

        selectedTopics.isEmpty() -> memos.filter { memo ->
            selectedMoods.contains(memo.mood.toVM())
        }

        else -> memos.filter { memo ->
            memo.topics.isNotEmpty() &&
                    selectedMoods.contains(memo.mood.toVM()) &&
                    memo.topics.any { it in selectedTopics }
        }
    }

    fun onClearMood() = filterState.update { it.copy(selectedMoods = emptyList()) }
    fun onClearTopic() = filterState.update { it.copy(selectedTopics = emptyList()) }
    fun onSelect(mood: MoodVM) = filterState.update { filterState ->
        filterState.copy(
            selectedMoods = filterState.selectedMoods.toMutableStateList().apply {
                toggle(mood)
                if (size == MoodVM.entries.size) removeAll(this)
            }
        )
    }

    fun onSelect(topic: String) = filterState.update { filterState ->
        filterState.copy(
            selectedTopics = filterState.selectedTopics.toMutableStateList().apply {
                toggle(topic)
                sort()
            }
        )
    }

    fun <T> SnapshotStateList<T>.toggle(item: T) {
        if (contains(item)) {
            remove(item)
        } else {
            add(item)
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

    fun onClickShowMore() {
        descriptionMaxLines.update { Int.MAX_VALUE }
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
