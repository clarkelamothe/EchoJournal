package com.clarkelamothe.echojournal.core.database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceMemoDao {
    @Query("SELECT * FROM VoiceMemoEntity")
    fun getAll(): Flow<List<VoiceMemoEntity>>
}
