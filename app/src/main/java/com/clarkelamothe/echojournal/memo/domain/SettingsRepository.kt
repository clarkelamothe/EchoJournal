package com.clarkelamothe.echojournal.memo.domain

import com.clarkelamothe.echojournal.core.domain.Settings

interface SettingsRepository {
    suspend fun save(settings: Settings)
}