package com.clarkelamothe.echojournal.memo.data

import com.clarkelamothe.echojournal.core.database.SettingsEntity
import com.clarkelamothe.echojournal.core.domain.Settings

fun SettingsEntity.toBM() = with(this) {
    Settings(
        id = id,
        mood = mood,
        topics = topics
    )
}

fun Settings.toDM() = with(this) {
    SettingsEntity(
        id = id,
        mood = mood,
        topics = topics
    )
}