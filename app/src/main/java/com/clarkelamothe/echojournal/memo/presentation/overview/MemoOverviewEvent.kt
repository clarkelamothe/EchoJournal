package com.clarkelamothe.echojournal.memo.presentation.overview

sealed interface MemoOverviewEvent {
    data class VoiceMemoRecorded(val filePath: String) : MemoOverviewEvent
}
