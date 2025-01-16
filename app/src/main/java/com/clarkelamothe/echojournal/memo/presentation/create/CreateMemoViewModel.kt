package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class CreateMemoViewModel(
    private val filePath: String
) : ViewModel() {
    private val eventChannel = Channel<CreateMemoEvent>()
    val events = eventChannel.receiveAsFlow()

    var state by mutableStateOf(CreateMemoState())
        private set

    private val showBottomSheet = MutableStateFlow(false)
    private val memoState = MutableStateFlow(MemoState())
    private val playProgress = MutableStateFlow(0f)

    init {
        combine(
            showBottomSheet,
            memoState
        ) { showBottomSheet, memoState ->

            state = state.copy(
                showBottomSheet = showBottomSheet,
                memoTitle = memoState.title,
                topics = memoState.topics,
                canSave = memoState.title.isNotBlank() && memoState.mood != null,
                mood = memoState.mood,
                description = memoState.description
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: CreateMemoAction) {
        when (action) {
            CreateMemoAction.OnAiClick -> {}
            CreateMemoAction.OnPlayClick -> {}
            CreateMemoAction.OnSaveClick -> {}
            is CreateMemoAction.OnAddDescription -> {
                memoState.update { state ->
                    state.copy(
                        description = state.description
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
    val playProgress: Float = 0f
)

data class MemoState(
    val title: String = "",
    val description: String = "",
    val topics: List<String> = emptyList(),
    val mood: MoodVM? = null
)
