package com.clarkelamothe.echojournal.core.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.clarkelamothe.echojournal.core.domain.Mood

@Entity
data class VoiceMemoEntity(
    @PrimaryKey(autoGenerate = true)
    val memoId: Long = 0,
    val title: String,
    val date: String,
    val time: String,
    val description: String,
    val filePath: String,
    val mood: Mood,
    val duration: String
)

@Entity
data class Topic(
    @PrimaryKey(autoGenerate = true)
    val topicId: Long = 0,
    val topicTitle: String
)

@Entity(primaryKeys = ["memoId", "topicId"])
data class MemoTopicCrossRef(
    val memoId: Long,
    val topicId: Long
)

data class VoiceMemoWithTopics(
    @Embedded val voiceMemo: VoiceMemoEntity,
    @Relation(
        parentColumn = "memoId",
        entityColumn = "topicId",
        associateBy = Junction(MemoTopicCrossRef::class)
    )
    val topics: List<Topic>
)

data class TopicWithVoiceMemos(
    @Embedded
    val topic: Topic,
    @Relation(
        parentColumn = "topicId",
        entityColumn = "memoId",
        associateBy = Junction(MemoTopicCrossRef::class)
    )
    val voiceMemos: VoiceMemoEntity
)