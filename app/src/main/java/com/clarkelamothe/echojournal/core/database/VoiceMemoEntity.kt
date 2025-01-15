package com.clarkelamothe.echojournal.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clarkelamothe.echojournal.core.domain.MoodBM

@Entity
data class VoiceMemoEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val dateTime: String,
    val description: String,
    val audio: String,
    val moodBM: MoodBM,
    val topics: List<String>
)
