package com.clarkelamothe.echojournal.core.database

import androidx.room.Dao
import androidx.room.Upsert

@Dao
interface SettingsDao {
    @Upsert
    fun upsert(settings: SettingsEntity)
}