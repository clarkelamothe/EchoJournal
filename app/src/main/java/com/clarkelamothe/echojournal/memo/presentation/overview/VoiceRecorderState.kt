package com.clarkelamothe.echojournal.memo.presentation.overview

import com.clarkelamothe.echojournal.core.presentation.designsystem.RecordingState

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
