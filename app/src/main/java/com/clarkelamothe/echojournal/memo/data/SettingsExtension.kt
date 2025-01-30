package com.clarkelamothe.echojournal.memo.data

import com.clarkelamothe.echojournal.core.database.SettingsEntity
import com.clarkelamothe.echojournal.core.domain.Settings

fun SettingsEntity?.toBM() = with(this) {
    Settings(
        mood = this?.mood,
        topics = this?.topics ?: emptyList()
    )
}

fun Settings.toDM() = with(this) {
    SettingsEntity(
        mood = mood,
        topics = topics
    )
}