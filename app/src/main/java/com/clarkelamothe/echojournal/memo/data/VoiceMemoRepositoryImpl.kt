package com.clarkelamothe.echojournal.memo.data

import com.clarkelamothe.echojournal.EchoJournalApp
import com.clarkelamothe.echojournal.core.database.Topic
import com.clarkelamothe.echojournal.core.database.VoiceMemoDao
import com.clarkelamothe.echojournal.core.database.VoiceMemoEntity
import com.clarkelamothe.echojournal.core.database.VoiceMemoWithTopics
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
        return dao.getMemoWithTopics().map {
            it.map { memos ->
                VoiceMemo(
                    id = memos.voiceMemo.memoId,
                    title = memos.voiceMemo.title,
                    date = memos.voiceMemo.date,
                    time = memos.voiceMemo.time,
                    description = memos.voiceMemo.description,
                    filePath = memos.voiceMemo.filePath,
                    moodBM = memos.voiceMemo.moodBM,
                    topics = memos.topics.map { it.topicTitle }
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
                    moodBM = voiceMemo.moodBM
                ),
                topics = voiceMemo.topics.map { Topic(topicTitle = it) }
            )
        )
    }

    override fun getAllTopics(): Flow<List<String>> {
        return dao.getAllTopics()
    }
}
