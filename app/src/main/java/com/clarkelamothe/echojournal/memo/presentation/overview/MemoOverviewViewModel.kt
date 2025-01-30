@file:Suppress("OPT_IN_USAGE")

package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.designsystem.PlayerState
import com.clarkelamothe.echojournal.core.presentation.designsystem.RecordingState
import com.clarkelamothe.echojournal.core.presentation.ui.mappers.toVM
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM
import com.clarkelamothe.echojournal.memo.domain.AudioPlayer
import com.clarkelamothe.echojournal.memo.domain.AudioRecorder
import com.clarkelamothe.echojournal.memo.domain.FileManager
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import com.clarkelamothe.echojournal.memo.presentation.formatDate
import com.clarkelamothe.echojournal.memo.presentation.formatDuration
import com.clarkelamothe.echojournal.memo.presentation.formatTime
import com.clarkelamothe.echojournal.memo.presentation.toElapsedTimeFormatted
import com.clarkelamothe.echojournal.memo.presentation.toLocalTime
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class MemoOverviewViewModel(
    repository: VoiceMemoRepository,
    private val player: AudioPlayer,
    private val recorder: AudioRecorder,
    private val fileManager: FileManager = EchoJournalApp.fileManager
) : ViewModel() {
    private var recordingFilePath: String = ""

    var state by mutableStateOf(MemoOverviewState())
        private set

    private val filterState = MutableStateFlow(FilterState())

    private val voiceRecorderState = MutableStateFlow(VoiceRecorderState())
    private val shouldStartTimer = MutableStateFlow(false)
    private val observeAmplitudes = MutableStateFlow(false)
    private val lastEmitted = MutableStateFlow(Duration.ZERO)
    private val descriptionMaxLines = MutableStateFlow(3)
    private val observeCurrentPlayingAudio = MutableStateFlow(false)
    private val currentPlayingAudio = MutableStateFlow(CurrentPlayingAudio())

    private val eventChannel = Channel<MemoOverviewEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        combine(
            repository.getAll(),
            filterState,
            voiceRecorderState,
            descriptionMaxLines,
            currentPlayingAudio,
        ) { memos, filterState, voiceRecorder, maxLines, currentPlayingAudio ->
            val selectedTopics = filterState.selectedTopics
            val selectedMoods = filterState.selectedMoods

            val voiceMemos = filteredVoiceMemos(selectedMoods, selectedTopics, memos)

            state = state.copy(
                showEmptyState = memos.isEmpty(),
                moodChipLabel = filterState.moodLabel(selectedMoods),
                topicChipLabel = filterState.topicsLabel(
                    selectedTopics,
                    filterState.topics
                ),
                selectedMood = selectedMoods,
                selectedTopics = selectedTopics,
                voiceRecorderState = voiceRecorder,
                memos = sortedVoiceMemos(voiceMemos),
                topics = filterState.topics,
                descriptionMaxLine = maxLines,
                currentPlayingAudio = currentPlayingAudio
            )
        }.launchIn(viewModelScope)

        repository.getAllTopics()
            .onEach { initialTopics ->
                filterState.update {
                    it.copy(topics = initialTopics)
                }
            }
            .launchIn(viewModelScope)

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

        observeCurrentPlayingAudio
            .flatMapLatest {
                player.observerPosition(it)
            }
            .distinctUntilChanged()
            .onEach {
                currentPlayingAudio
                    .update { currentPlayingAudio ->
                        currentPlayingAudio.copy(
                            elapsedTime = it.formatDuration(),
                            progress = (it / player.duration()).coerceIn(0.0, 1.0).toFloat()
                        )
                    }
            }
            .launchIn(viewModelScope)
    }

    private fun sortedVoiceMemos(voiceMemos: List<VoiceMemo>) =
        voiceMemos
            .map {
                it.copy(
                    date = it.date,
                    time = it.time.formatTime().toLocalTime(),
                    duration = it.duration
                )
            }.sortedByDescending { it.date }
            .groupBy { it.date }
            .mapKeys { it.key.formatDate().uppercase() }
            .mapValues { values ->
                values.value.sortedByDescending { it.time }
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

    fun startRecording() {
        onStartTimer(true)
        voiceRecorderState.update {
            it.copy(
                state = RecordingState.Recording,
                title = RecordingState.Recording.getTitle()
            )
        }
        recordingFilePath = recorder.start("memo")
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
        fileManager.getFile(recordingFilePath)?.delete()
    }

    fun finishRecording() {
        onStartTimer(false)
        showBottomSheet(false)
        lastEmitted.update { Duration.ZERO }
        recorder.stop()
        viewModelScope.launch {
            eventChannel.send(MemoOverviewEvent.VoiceMemoRecorded(recordingFilePath))
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

    fun onClickPlay(filePath: String) {
        player.stop()
        observeCurrentPlayingAudio.update { false }

        currentPlayingAudio.apply {
            player.init(filePath)
            update {
                it.copy(
                    state = PlayerState.Playing,
                    filePath = filePath
                )
            }
            player.start {
                update { it.copy(state = PlayerState.Idle) }
                player.stop()
                observeCurrentPlayingAudio.update { false }
            }
            observeCurrentPlayingAudio.update { true }
        }
    }

    fun onClickPause() {
        player.pause()
        currentPlayingAudio.update { it.copy(state = PlayerState.Paused) }
    }

    fun onClickResume() {
        player.resume()
        currentPlayingAudio.update { it.copy(state = PlayerState.Playing) }
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

data class CurrentPlayingAudio(
    val state: PlayerState = PlayerState.Idle,
    val filePath: String = "",
    val progress: Float = 0f,
    val elapsedTime: String = "0:00",
    val duration: String = ""
)