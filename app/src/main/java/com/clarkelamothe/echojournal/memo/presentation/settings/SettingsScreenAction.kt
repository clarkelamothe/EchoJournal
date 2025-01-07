package com.clarkelamothe.echojournal.memo.presentation.settings

sealed interface SettingsScreenAction {
    data object OnBackClick : SettingsScreenAction
}
