package com.clarkelamothe.echojournal.memo.presentation.overview

sealed interface MemoOverviewEvent {
    data object VoiceMemoRecorded : MemoOverviewEvent
}
