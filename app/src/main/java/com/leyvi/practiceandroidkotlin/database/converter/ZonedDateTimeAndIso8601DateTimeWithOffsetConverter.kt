package com.leyvi.practiceandroidkotlin.database.converter

import androidx.room.TypeConverter
import java.time.ZonedDateTime

class ZonedDateTimeAndIso8601DateTimeWithOffsetConverter {

    @TypeConverter
    fun toZonedDateTime(iso8601DateTimeWithOffset: String?): ZonedDateTime? {
        return iso8601DateTimeWithOffset?.let {
            ZonedDateTime.parse(it)
        }
    }

    @TypeConverter
    fun toIso8601String(zonedDateTime: ZonedDateTime?): String? {
        return zonedDateTime?.toString()
    }
}
