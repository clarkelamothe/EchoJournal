package com.clarkelamothe.echojournal.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

private const val DB_NAME = "echo-journal-db"

@Database(
    entities = [VoiceMemoEntity::class, Topic::class, MemoTopicCrossRef::class, SettingsEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class EchoJournalDatabase : RoomDatabase() {
    abstract fun voiceMemoDao(): VoiceMemoDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        fun db(context: Context) = Room.databaseBuilder(
            context,
            EchoJournalDatabase::class.java,
            DB_NAME
        ).build()
    }
}
