package com.clarkelamothe.echojournal.memo.domain

import com.clarkelamothe.echojournal.core.domain.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun save(settings: Settings)

    fun get(): Flow<Settings>
}