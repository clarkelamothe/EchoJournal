package com.clarkelamothe.echojournal.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clarkelamothe.echojournal.core.domain.Mood
import com.clarkelamothe.echojournal.core.domain.SETTINGS_ID

@Entity
data class SettingsEntity(
    @PrimaryKey
    val id: Long = SETTINGS_ID,
    val mood: Mood?,
    val topics: List<String>
)
