package com.clarkelamothe.echojournal.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.clarkelamothe.echojournal.core.domain.MoodBM

@Entity
data class VoiceMemoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val date: String,
    val time: String,
    val description: String,
    val filePath: String,
    val moodBM: MoodBM,
    val topics: List<String>
)
