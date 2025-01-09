package com.clarkelamothe.echojournal.memo.presentation.create

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class CreateMemoViewModel : ViewModel() {
    private val eventChannel = Channel<CreateMemoEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: CreateMemoAction) {
        when (action) {
            CreateMemoAction.OnAddMoodClick -> {

            }
            CreateMemoAction.OnAiClick -> {}
            CreateMemoAction.OnPlayClick -> {}
            CreateMemoAction.OnSaveClick -> {}
            else -> {}
        }
    }
}
