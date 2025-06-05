package com.leyvi.practiceandroidkotlin.database.converter

import androidx.room.TypeConverter
import java.time.LocalDateTime

class LocalDateTimeAndIso8601DateTimeConverter {

    @TypeConverter
    fun toLocalDateTime(iso8601DateTime: String?): LocalDateTime? {
        return iso8601DateTime?.let {
            LocalDateTime.parse(it)
        }
    }

    @TypeConverter
    fun toIso8601String(localDateTime: LocalDateTime?): String? {
        return localDateTime?.toString()
    }
}