package com.clarkelamothe.echojournal.memo.data

import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.core.database.VoiceMemoDao
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime

class VoiceMemoRepositoryImpl(
    private val dao: VoiceMemoDao = EchoJournalApp.db.voiceMemoDao()
) : VoiceMemoRepository {
    override fun getAll(): Flow<List<VoiceMemo>> {
        return dao.getAll().map { memos ->
            memos.map {
                VoiceMemo(
                    id = it.id,
                    title = it.title,
                    dateTime = LocalDateTime.now(),  // "it.dateTime" to map to correct datetime
                    description = it.description,
                    audio = it.audio,
                    mood = it.mood,
                    topics = it.topics
                )
            }
        }
    }
}
