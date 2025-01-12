package com.clarkelamothe.echojournal.core.domain

import java.time.LocalDateTime

data class VoiceMemo(
    val id: Int,
    val title: String,
    val dateTime: LocalDateTime,
    val description: String,
    val audio: Any,
    val mood: Mood,
    val topics: List<String>
)
