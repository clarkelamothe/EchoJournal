package com.clarkelamothe.echojournal.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

private const val DB_NAME = "echo-journal-db"

@Database(entities = [VoiceMemoEntity::class, Topic::class, MemoTopicCrossRef::class], version = 1)
abstract class EchoJournalDatabase : RoomDatabase() {
    abstract fun voiceMemoDao(): VoiceMemoDao

    companion object {
        fun db(context: Context) = Room.databaseBuilder(
            context,
            EchoJournalDatabase::class.java,
            DB_NAME
        ).build()
    }
}
