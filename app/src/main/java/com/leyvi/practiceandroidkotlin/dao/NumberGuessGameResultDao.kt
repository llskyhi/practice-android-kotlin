package com.leyvi.practiceandroidkotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leyvi.practiceandroidkotlin.model.entity.NumberGuessGameResult

@Dao
interface NumberGuessGameResultDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun create(gameResult: NumberGuessGameResult)

    @Query(
        """
        SELECT
            *
        FROM number_guess_game_result
        """
    )
    fun getAll(): LiveData<List<NumberGuessGameResult>>

    // no update operations allowed

    @Delete
    fun delete(gameResult: NumberGuessGameResult)
}
