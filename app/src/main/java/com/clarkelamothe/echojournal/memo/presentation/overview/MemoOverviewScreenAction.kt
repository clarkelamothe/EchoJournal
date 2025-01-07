package com.clarkelamothe.echojournal.memo.presentation.overview

sealed interface MemoOverviewScreenAction {
    data object OnFabClick : MemoOverviewScreenAction
    data object OnSettingsClick : MemoOverviewScreenAction
}
