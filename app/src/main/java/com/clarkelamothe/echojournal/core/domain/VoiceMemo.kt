package com.clarkelamothe.echojournal.core.domain

data class VoiceMemo(
    val id: Int = 0,
    val title: String,
    val date: String,
    val time: String,
    val description: String,
    val filePath: String,
    val moodBM: MoodBM,
    val topics: List<String>
)
