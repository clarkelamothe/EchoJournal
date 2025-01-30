package com.clarkelamothe.echojournal.core.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun String.toList() = Json.decodeFromString<List<String>>(this)

    @TypeConverter
    fun List<String>.toList() = Json.encodeToString(this)
}
