package com.leyvi.practiceandroidkotlin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame.Status
import com.leyvi.practiceandroidkotlin.model.NumberGuessGameSettings
import com.leyvi.practiceandroidkotlin.model.entity.NumberGuessGameResult
import com.leyvi.practiceandroidkotlin.repo.NumberGuessGameResultRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class NumberGuessGameViewModel(
    private val numberGuessGameResultRepo: NumberGuessGameResultRepo,
) : ViewModel() {

    class NumberGuessGameViewModelFactory(
        private val numberGuessGameResultRepo: NumberGuessGameResultRepo,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NumberGuessGameViewModel::class.java)) {
                return NumberGuessGameViewModel(
                    numberGuessGameResultRepo,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    data class NumberGuessGameState(
        val gameStatus: Status,
        val guessCounter: Int,
        val rangeHintMin: Int,
        val rangeHintMax: Int,
    )

    companion object {
        private val TAG = NumberGuessGameViewModel::class.simpleName
    }

    private val numberGuessGameSettings = NumberGuessGameSettings()

    private lateinit var numberGuessGame: NumberGuessGame

    private val _secretNumber = MutableLiveData<Int>()
    private val _gameState = MutableLiveData<NumberGuessGameState>()

    val minSecretNumber: Int
        get() = numberGuessGame.minSecretNumber
    val maxSecretNumber: Int
        get() = numberGuessGame.maxSecretNumber
    val secretNumber: LiveData<Int> = _secretNumber
    val gameState: LiveData<NumberGuessGameState> = _gameState

    val gameResults: LiveData<List<NumberGuessGameResult>> = numberGuessGameResultRepo.getAll()

    init {
        setRange(
            minSecretNumber = numberGuessGameSettings.minSecretNumber,
            maxSecretNumber = numberGuessGameSettings.maxSecretNumber,
        )
    }

    fun guess(number: Int) {
        val nextGameStatus = numberGuessGame.guess(number)
        _gameState.value = NumberGuessGameState(
            gameStatus = nextGameStatus,
            guessCounter = numberGuessGame.guessCounter,
            rangeHintMin = numberGuessGame.rangeHintMin,
            rangeHintMax = numberGuessGame.rangeHintMax,
        )
    }

    fun restart() {
        numberGuessGame.reset()
        reset()
    }

    fun setRange(minSecretNumber: Int, maxSecretNumber: Int) {
        numberGuessGame = NumberGuessGame(
            minSecretNumber = minSecretNumber,
            maxSecretNumber = maxSecretNumber
        )
        reset()
    }

    fun recordGameResult(): Unit = recordResultIfHadWon()

    private fun reset() {
        _secretNumber.value = numberGuessGame.secretNumber
        _gameState.value = NumberGuessGameState(
            gameStatus = Status.INIT,
            guessCounter = numberGuessGame.guessCounter,
            rangeHintMin = numberGuessGame.rangeHintMin,
            rangeHintMax = numberGuessGame.rangeHintMax,
        )
    }

    private fun recordResultIfHadWon() {
        if (gameState.value!!.gameStatus != Status.BINGO) {
            Log.d(TAG, "recordResultIfBingo: not recording game result because of not winning")
            return
        }

        val numberGuessGameResult = NumberGuessGameResult(
            minSecretNumber = minSecretNumber,
            maxSecretNumber = maxSecretNumber,
            guessCount = gameState.value!!.guessCounter,
            createdAt = ZonedDateTime.now(),
        )
        Log.d(TAG, "recordResultIfBingo: recording game result $numberGuessGameResult")
        viewModelScope.launch(Dispatchers.IO) {
            numberGuessGameResultRepo.create(numberGuessGameResult)
        }
    }
}