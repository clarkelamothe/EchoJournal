package com.clarkelamothe.echojournal.core.domain

data class VoiceMemo(
    val id: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val description: String,
    val filePath: String,
    val mood: Mood,
    val topics: List<String> = emptyList()
)
