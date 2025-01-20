package com.clarkelamothe.echojournal.memo.domain

import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import kotlinx.coroutines.flow.Flow

interface VoiceMemoRepository {
    fun getAll(): Flow<List<VoiceMemo>>
    fun filterTopics(input: String): Flow<List<String>>
    suspend fun save(voiceMemo: VoiceMemo)
    fun getAllTopics(): Flow<List<String>>
}
