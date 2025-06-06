package com.leyvi.practiceandroidkotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.leyvi.practiceandroidkotlin.model.entity.ProfitCenter

@Dao
interface ProfitCenterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(profitCenter: ProfitCenter)

    @Query(
        """
        SELECT            
            *
        FROM profit_center
        """
    )
    fun getAll(): LiveData<List<ProfitCenter>>

    @Delete
    fun delete(profitCenter: ProfitCenter)
}