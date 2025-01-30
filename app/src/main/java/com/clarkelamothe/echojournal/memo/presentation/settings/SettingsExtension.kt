package com.clarkelamothe.echojournal.memo.presentation.settings

import com.clarkelamothe.echojournal.core.domain.Settings
import com.clarkelamothe.echojournal.core.presentation.ui.mappers.toBM
import com.clarkelamothe.echojournal.core.presentation.ui.mappers.toVM

fun Settings.toVM() = with(this) {
    SettingsVM(
        moodVM = mood?.toVM(),
        topics = topics
    )
}

fun SettingsVM.toBM() = with(this) {
    Settings(
        mood = moodVM?.toBM(),
        topics = topics
    )
}
