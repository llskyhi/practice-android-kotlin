package com.leyvi.practiceandroidkotlin.repo

import androidx.lifecycle.LiveData
import com.leyvi.practiceandroidkotlin.dao.ProfitCenterDao
import com.leyvi.practiceandroidkotlin.model.entity.ProfitCenter

class ProfitCenterRepo(
    private val profitCenterDao: ProfitCenterDao,
) {
    suspend fun create(profitCenter: ProfitCenter) {

    }

    fun getAll(): LiveData<List<ProfitCenter>> = profitCenterDao.getAll()

    suspend fun update(profitCenter: ProfitCenter) {
        profitCenterDao.save(profitCenter)
    }

    suspend fun delete(profitCenter: ProfitCenter) {
        profitCenterDao.delete(profitCenter)
    }
}
