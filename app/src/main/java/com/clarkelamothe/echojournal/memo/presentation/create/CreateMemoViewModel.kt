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
    private val memoTitle = MutableStateFlow("")
    private val memoTopics = MutableStateFlow<List<String>>(emptyList())
    private val memoMood = MutableStateFlow<MoodVM?>(null)
    private val description = MutableStateFlow<String>("")

    init {
        combine(
            showBottomSheet,
            memoTitle,
            memoTopics,
            memoMood,
            description
        ) { showBottomSheet, title, topics, mood, description ->

            state = state.copy(
                showBottomSheet = showBottomSheet,
                memoTitle = title,
                topics = topics,
                canSave = title.isNotBlank() && mood != null,
                mood = mood,
                description = description
            )
        }.launchIn(viewModelScope)
    }

    fun onAction(action: CreateMemoAction) {
        when (action) {
            CreateMemoAction.OnAiClick -> {}
            CreateMemoAction.OnPlayClick -> {}
            CreateMemoAction.OnSaveClick -> {}
            CreateMemoAction.DismissBottomSheet -> {
                memoMood.update { null }
                showBottomSheet.update { false }
            }

            CreateMemoAction.OnAddMoodClick -> {
                showBottomSheet.update { true }
            }

            is CreateMemoAction.OnCancelMoodClick -> {
                memoMood.update { null }
                showBottomSheet.update { false }
            }

            is CreateMemoAction.OnConfirmMoodClick -> {
                memoMood.update { it }
                showBottomSheet.update { false }
            }

            is CreateMemoAction.OnSelectMood -> memoMood.update { action.moodVM }

            is CreateMemoAction.OnTitleChange -> updateTitle(action.title)
            is CreateMemoAction.OnRemoveTopic -> {
                memoTopics.update {
                    it.toMutableStateList().apply {
                        removeAt(action.index)
                    }
                }
            }

            is CreateMemoAction.OnAddTopic -> {
                memoTopics.update {
                    it.toMutableStateList().apply {
                        add(action.topic)
                        sort()
                    }
                }
            }

            else -> {}
        }
    }

    private fun updateTitle(title: String) {
        memoTitle.update { title }
    }
}

data class CreateMemoState(
    val showBottomSheet: Boolean = false,
    val memoTitle: String = "",
    val mood: MoodVM? = null,
    val topics: List<String> = emptyList(),
    val description: String = "",
    val canSave: Boolean = false
)
