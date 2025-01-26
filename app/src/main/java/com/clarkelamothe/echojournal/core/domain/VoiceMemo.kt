package com.clarkelamothe.echojournal.core.domain

import java.time.LocalDate
import java.time.LocalTime

data class VoiceMemo(
    val id: Long = 0,
    val title: String,
    val date: LocalDate,
    val time: LocalTime,
    val description: String,
    val filePath: String,
    val mood: Mood,
    val topics: List<String> = emptyList(),
    val duration: String
)
