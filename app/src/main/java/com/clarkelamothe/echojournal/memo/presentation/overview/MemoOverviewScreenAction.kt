package com.clarkelamothe.echojournal.memo.presentation.overview

sealed interface MemoOverviewScreenAction {
    data object OnSettingsClick : MemoOverviewScreenAction
    data object OnStartRecording : MemoOverviewScreenAction {}

    data object OnCancelRecording : MemoOverviewScreenAction

    data object OnFinishRecording : MemoOverviewScreenAction
}
