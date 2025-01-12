package com.clarkelamothe.echojournal.memo.domain

import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import kotlinx.coroutines.flow.Flow

interface VoiceMemoRepository {
    fun getAll(): Flow<List<VoiceMemo>>
}
