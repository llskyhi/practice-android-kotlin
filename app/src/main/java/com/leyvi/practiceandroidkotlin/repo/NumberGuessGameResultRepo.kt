package com.leyvi.practiceandroidkotlin.repo

import androidx.lifecycle.LiveData
import com.leyvi.practiceandroidkotlin.dao.NumberGuessGameResultDao
import com.leyvi.practiceandroidkotlin.model.entity.NumberGuessGameResult

class NumberGuessGameResultRepo(
    private val numberGuessGameResultDao: NumberGuessGameResultDao
) {

    suspend fun create(gameResult: NumberGuessGameResult) {
        return numberGuessGameResultDao.create(gameResult)
    }

    fun getAll(): LiveData<List<NumberGuessGameResult>> {
        return numberGuessGameResultDao.getAll()
    }

    suspend fun delete(gameResult: NumberGuessGameResult) {
        return numberGuessGameResultDao.delete(gameResult)
    }
}
