package com.clarkelamothe.echojournal.core.database

import androidx.room.TypeConverter
import com.clarkelamothe.echojournal.core.domain.Mood
import kotlinx.serialization.json.Json

class TypeConverters {

    @TypeConverter
    fun toString(value: Mood) = value.title


    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<Mood>>(value)

    @TypeConverter
    fun listToString(value: List<String>) = Json.encodeToString(value.distinct())

    @TypeConverter
    fun listToString(value: String) = Json.decodeFromString<List<String>>(value)
}
