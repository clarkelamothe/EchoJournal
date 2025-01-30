package com.clarkelamothe.echojournal.memo.presentation.settings

import com.clarkelamothe.echojournal.core.presentation.ui.model.MoodVM

sealed interface SettingsScreenAction {
    data class OnMoodSelect(val mood: MoodVM) : SettingsScreenAction
    data class OnRemoveTopic(val index: Int) : SettingsScreenAction
    data class OnAddTopic(val topic: String) : SettingsScreenAction
    data class OnInputTopic(val text: CharSequence) : SettingsScreenAction
    data object OnBackClick : SettingsScreenAction
    data object DismissDropdown : SettingsScreenAction
}
