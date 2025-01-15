package com.clarkelamothe.echojournal.core.database

import androidx.room.TypeConverter
import com.clarkelamothe.echojournal.core.domain.MoodBM
import kotlinx.serialization.json.Json

class TypeConverters {

    @TypeConverter
    fun toString(value: MoodBM) = value.title


    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<List<MoodBM>>(value)

    @TypeConverter
    fun listToString(value: List<String>) = Json.encodeToString(value.distinct())

    @TypeConverter
    fun listToString(value: String) = Json.decodeFromString<List<String>>(value)
}
