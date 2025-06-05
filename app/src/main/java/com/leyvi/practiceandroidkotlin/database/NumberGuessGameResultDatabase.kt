package com.leyvi.practiceandroidkotlin.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leyvi.practiceandroidkotlin.dao.NumberGuessGameResultDao
import com.leyvi.practiceandroidkotlin.database.converter.ZonedDateTimeAndIso8601DateTimeWithOffsetConverter
import com.leyvi.practiceandroidkotlin.model.entity.NumberGuessGameResult

@Database(
    entities = [
        NumberGuessGameResult::class
    ],
    version = 1,
)
@TypeConverters(
    value = [
        ZonedDateTimeAndIso8601DateTimeWithOffsetConverter::class,
    ],
)
abstract class NumberGuessGameResultDatabase : RoomDatabase() {
    companion object {
        private const val dbFileName = "number-guess-game-result.db"
        private val TAG = NumberGuessGameResultDatabase::class.simpleName

        private var singletonInstance: NumberGuessGameResultDatabase? = null

        fun getInstance(context: Context): NumberGuessGameResultDatabase {
            synchronized(this) {
                if (singletonInstance == null) {
                    Log.d(TAG, "getInstance: initializing database..")
                    singletonInstance = Room.databaseBuilder(
                        context,
                        NumberGuessGameResultDatabase::class.java,
                        dbFileName,
                    ).build()
                } else {
                    Log.d(TAG, "getInstance: returning existing database instance.")
                }
                return singletonInstance!!
            }
        }
    }

    abstract fun numberGuessGameResultDao(): NumberGuessGameResultDao
}
