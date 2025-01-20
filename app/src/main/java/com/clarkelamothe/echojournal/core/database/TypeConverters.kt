package com.clarkelamothe.echojournal.core.database

import androidx.room.TypeConverter
import com.clarkelamothe.echojournal.core.domain.MoodBM

class TypeConverters {
    @TypeConverter
    fun toString(value: MoodBM) = value.title
}
