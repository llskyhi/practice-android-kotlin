package com.leyvi.practiceandroidkotlin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.leyvi.practiceandroidkotlin.dao.ProfitCenterDao
import com.leyvi.practiceandroidkotlin.model.entity.ProfitCenter

@Database(
    entities = [
        ProfitCenter::class,
    ],
    version = 1,
)
abstract class CrudPracticeDatabase : RoomDatabase() {

    abstract fun getProfitCenterDao(): ProfitCenterDao

    companion object {
        const val DATABASE_FILE_NAME = "crud-practice-database.db"

        private lateinit var instance: CrudPracticeDatabase

        fun getInstance(context: Context): CrudPracticeDatabase {
            synchronized(this) {
                if (!this::instance.isInitialized) {
                    instance = databaseBuilder(
                        context,
                        CrudPracticeDatabase::class.java,
                        DATABASE_FILE_NAME,
                    ).build()
                }
                return instance
            }
        }
    }
}
