package com.clarkelamothe.echojournal.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Upsert
    fun upsert(settings: SettingsEntity)

    @Query("SELECT * FROM SettingsEntity")
    fun getSettings(): Flow<SettingsEntity>
}