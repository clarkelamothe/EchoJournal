package com.clarkelamothe.echojournal.memo.data

import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.core.database.VoiceMemoDao
import com.clarkelamothe.echojournal.core.database.VoiceMemoEntity
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime

class VoiceMemoRepositoryImpl(
    private val dao: VoiceMemoDao = EchoJournalApp.db.voiceMemoDao()
) : VoiceMemoRepository {
    override fun getAll(): Flow<List<VoiceMemo>> {
        return dao.getAll().map { memos ->
            memos.map {
                VoiceMemo(
                    id = it.id,
                    title = it.title,
                    date = it.date,
                    time = it.time,
                    description = it.description,
                    filePath = it.filePath,
                    moodBM = it.moodBM,
                    topics = it.topics
                )
            }
        }
    }

    override suspend fun save(voiceMemo: VoiceMemo) {
        dao.upsert(
            VoiceMemoEntity(
                title = voiceMemo.title,
                date = LocalDate.now().toString(),
                time = LocalTime.now().toString(),
                description = voiceMemo.description,
                filePath = voiceMemo.filePath,
                moodBM = voiceMemo.moodBM,
                topics = voiceMemo.topics
            )
        )
    }
}
