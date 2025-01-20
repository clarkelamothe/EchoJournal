package com.clarkelamothe.echojournal.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceMemoDao {
    @Query("SELECT * FROM VoiceMemoEntity")
    fun getAll(): Flow<List<VoiceMemoEntity>>

    @Query("SELECT DISTINCT topicTitle FROM Topic")
    fun getAllTopics(): Flow<List<String>>

    @Upsert
    suspend fun upsert(voiceMemo: VoiceMemoEntity): Long

    @Upsert
    suspend fun upsert(topic: Topic): Long

    @Query("SELECT * FROM Topic WHERE topicTitle = :title LIMIT 1")
    suspend fun getTopicByTitle(title: String): Topic?

    @Transaction
    @Query("SELECT topicTitle FROM Topic WHERE topicTitle LIKE :input")
    fun getTopicsWithMemo(input: String): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemoTopicCrossRef(crossRef: MemoTopicCrossRef)

    @Transaction
    @Query("SELECT * FROM voicememoentity")
    fun getMemoWithTopics(): Flow<List<VoiceMemoWithTopics>>

    @Transaction
    suspend fun upsertMemoWithTopic(voiceMemoWithTopics: VoiceMemoWithTopics) {
        val memoId = upsert(voiceMemoWithTopics.voiceMemo)
        voiceMemoWithTopics.topics.forEach {
            val existing = getTopicByTitle(it.topicTitle)

            val finalTopicId = when {
                existing != null -> existing.topicId
                else -> {
                    val newTopicId = upsert(it)
                    if (newTopicId != -1L) newTopicId else it.topicId
                }
            }
            val crossRef = MemoTopicCrossRef(
                memoId = memoId,
                topicId = finalTopicId
            )
            insertMemoTopicCrossRef(crossRef)
        }
    }
}
