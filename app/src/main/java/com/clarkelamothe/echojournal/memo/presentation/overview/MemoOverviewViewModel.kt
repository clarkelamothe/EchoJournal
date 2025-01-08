package com.clarkelamothe.echojournal.memo.presentation.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MemoOverviewViewModel : ViewModel() {
    private val eventChannel = Channel<MemoOverviewEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: MemoOverviewScreenAction) {
        when (action) {
            MemoOverviewScreenAction.OnFinishRecording -> viewModelScope.launch {
                eventChannel.send(MemoOverviewEvent.VoiceMemoRecorded)
            }

            else -> {}
        }
    }
}
