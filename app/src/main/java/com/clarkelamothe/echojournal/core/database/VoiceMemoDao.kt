package com.clarkelamothe.echojournal.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceMemoDao {
    @Query("SELECT * FROM VoiceMemoEntity")
    fun getAll(): Flow<List<VoiceMemoEntity>>

    @Upsert
    suspend fun upsert(voiceMemo: VoiceMemoEntity)

    @Query("SELECT topics FROM VoiceMemoEntity WHERE topics LIKE :input")
    fun getAllTopics(input: String): Flow<List<String>>
}
