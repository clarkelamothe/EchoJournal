package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.core.presentation.designsystem.PlayerState
import com.clarkelamothe.echojournal.core.presentation.ui.mappers.toBM
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM
import com.clarkelamothe.echojournal.memo.domain.AudioPlayer
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class CreateMemoViewModel(
    private val filePath: String,
    private val audioPlayer: AudioPlayer,
    private val repository: VoiceMemoRepository
) : ViewModel() {
    private val eventChannel = Channel<CreateMemoEvent>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(CreateMemoState())
        private set

    private val showBottomSheet = MutableStateFlow(false)
    private val memoState = MutableStateFlow(MemoState())
    private val player = MutableStateFlow(Player())

    init {
        audioPlayer.init(filePath = filePath)
        setAudioDuration()

        combine(
            showBottomSheet,
            memoState,
            player
        ) { showBottomSheet, memoState, player ->

            state = state.copy(
                showBottomSheet = showBottomSheet,
                memoTitle = memoState.title,
                topics = memoState.topics,
                canSave = memoState.title.isNotBlank() && memoState.mood != null,
                mood = memoState.mood,
                description = memoState.description,
                playProgress = player.progress,
                playerState = player.state,
                duration = player.duration
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: CreateMemoAction) {
        when (action) {
            CreateMemoAction.OnAiClick -> {

            }

            CreateMemoAction.OnPlayClick -> {
                with(player) {
                    audioPlayer.start {
                        update {
                            it.copy(
                                state = PlayerState.Playing
                            )
                        }
                    }
                }
            }

            CreateMemoAction.OnPauseClick -> {
                audioPlayer.pause()
                player.update { it.copy(state = PlayerState.Paused) }
            }

            CreateMemoAction.OnCancelClick -> {
                audioPlayer.stop()
                player.update { it.copy(state = PlayerState.Idle) }
                viewModelScope.launch {
                    eventChannel.send(CreateMemoEvent.MemoCancelled)
                }
            }

            CreateMemoAction.OnSaveClick -> {
                audioPlayer.stop()
                player.update { it.copy(state = PlayerState.Idle) }

                viewModelScope.launch {
                    with(memoState.value) {
                        repository.save(
                            VoiceMemo(
                                id = 0,
                                title = title,
                                date = LocalDate.now().toString(),
                                time = LocalTime.now().toString(),
                                description = description,
                                filePath = filePath,
                                moodBM = mood!!.toBM(),
                                topics = topics
                            )
                        )
                    }
                }

                viewModelScope.launch {
                    eventChannel.send(CreateMemoEvent.MemoSaved)
                }
            }

            is CreateMemoAction.OnAddDescription -> {
                memoState.update { state ->
                    state.copy(
                        description = action.description
                    )
                }
            }

            CreateMemoAction.DismissBottomSheet -> {
                memoState.update { it.copy(mood = null) }
                showBottomSheet.update { false }
            }

            CreateMemoAction.OnAddMoodClick -> {
                showBottomSheet.update { true }
            }

            is CreateMemoAction.OnCancelMoodClick -> {
                memoState.update { it.copy(mood = null) }
                showBottomSheet.update { false }
            }

            is CreateMemoAction.OnConfirmMoodClick -> {
                memoState.update { it }
                showBottomSheet.update { false }
            }

            is CreateMemoAction.OnSelectMood -> memoState.update { it.copy(mood = action.moodVM) }

            is CreateMemoAction.OnTitleChange -> updateTitle(action.title)
            is CreateMemoAction.OnRemoveTopic -> {
                memoState.update { state ->
                    state.copy(
                        topics = state.topics.toMutableStateList().apply {
                            removeAt(action.index)
                        }
                    )
                }
            }

            is CreateMemoAction.OnAddTopic -> {
                memoState.update { state ->
                    state.copy(
                        topics = state.topics.toMutableStateList().apply {
                            add(action.topic)
                            sort()
                        }
                    )
                }
            }

            else -> {}
        }
    }

    private fun setAudioDuration() {
        player.update { it.copy(duration = audioPlayer.duration()) }
    }

    private fun updateTitle(title: String) {
        memoState.update { it.copy(title = title) }
    }
}

data class CreateMemoState(
    val showBottomSheet: Boolean = false,
    val memoTitle: String = "",
    val mood: MoodVM? = null,
    val topics: List<String> = emptyList(),
    val description: String = "",
    val canSave: Boolean = false,
    val playProgress: Float = 0f,
    val playerState: PlayerState = PlayerState.Idle,
    val duration: Int = 0
)

data class MemoState(
    val title: String = "",
    val description: String = "",
    val topics: List<String> = emptyList(),
    val mood: MoodVM? = null
)

data class Player(
    val state: PlayerState = PlayerState.Idle,
    val progress: Float = 0f,
    val duration: Int = 0
)