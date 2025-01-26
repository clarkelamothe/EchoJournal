package com.clarkelamothe.echojournal.memo.data

import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.core.database.Topic
import com.clarkelamothe.echojournal.core.database.VoiceMemoDao
import com.clarkelamothe.echojournal.core.database.VoiceMemoEntity
import com.clarkelamothe.echojournal.core.database.VoiceMemoWithTopics
import com.clarkelamothe.echojournal.core.domain.VoiceMemo
import com.clarkelamothe.echojournal.memo.domain.VoiceMemoRepository
import com.clarkelamothe.echojournal.memo.presentation.toLocalDate
import com.clarkelamothe.echojournal.memo.presentation.toLocalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalTime

class VoiceMemoRepositoryImpl(
    private val dao: VoiceMemoDao = EchoJournalApp.db.voiceMemoDao()
) : VoiceMemoRepository {
    override fun getAll(): Flow<List<VoiceMemo>> {
        return dao.getMemoWithTopics().map {
            it.map { memos ->
                VoiceMemo(
                    id = memos.voiceMemo.memoId,
                    title = memos.voiceMemo.title,
                    date = memos.voiceMemo.date.toLocalDate(),
                    time = memos.voiceMemo.time.toLocalTime(),
                    description = memos.voiceMemo.description,
                    filePath = memos.voiceMemo.filePath,
                    mood = memos.voiceMemo.mood,
                    topics = memos.topics.map { it.topicTitle },
                    duration = memos.voiceMemo.duration
                )
            }
        }
    }

    override fun filterTopics(input: String) = dao.getTopicsWithMemo("%${input.lowercase()}%")

    override suspend fun save(voiceMemo: VoiceMemo) {
        dao.upsertMemoWithTopic(
            VoiceMemoWithTopics(
                voiceMemo = VoiceMemoEntity(
                    title = voiceMemo.title,
                    date = LocalDate.now().toString(),
                    time = LocalTime.now().toString(),
                    description = voiceMemo.description,
                    filePath = voiceMemo.filePath,
                    mood = voiceMemo.mood,
                    duration = voiceMemo.duration
                ),
                topics = voiceMemo.topics.map { Topic(topicTitle = it) }
            )
        )
    }

    override fun getAllTopics(): Flow<List<String>> {
        return dao.getAllTopics()
    }
}
