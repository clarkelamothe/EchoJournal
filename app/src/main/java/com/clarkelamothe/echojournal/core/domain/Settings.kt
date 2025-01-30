package com.clarkelamothe.echojournal.core.domain

const val SETTINGS_ID = 0L

data class Settings(
    val id: Long = SETTINGS_ID,
    val mood: Mood?,
    val topics: List<String>
)
