package com.leyvi.practiceandroidkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame.Status
import com.leyvi.practiceandroidkotlin.model.NumberGuessGameSettings

class NumberGuessGameViewModel : ViewModel() {
    private val numberGuessGameSettings = NumberGuessGameSettings()

    private lateinit var numberGuessGame: NumberGuessGame

    private val _secretNumber = MutableLiveData<Int>()
    private val _gameStatus = MutableLiveData<Status>()
    private val _guessCounter = MutableLiveData<Int>()
    private val _rangeHintMin = MutableLiveData<Int>()
    private val _rangeHintMax = MutableLiveData<Int>()

    val minSecretNumber: Int
        get() = numberGuessGame.minSecretNumber
    val maxSecretNumber: Int
        get() = numberGuessGame.maxSecretNumber
    val secretNumber: LiveData<Int> = _secretNumber
    val gameStatus: LiveData<Status> = _gameStatus
    val guessCounter: LiveData<Int> = _guessCounter
    val rangeHintMin: LiveData<Int> = _rangeHintMin
    val rangeHintMax: LiveData<Int> = _rangeHintMax

    init {
        setRange(
            minSecretNumber = numberGuessGameSettings.minSecretNumber,
            maxSecretNumber = numberGuessGameSettings.maxSecretNumber,
        )
    }

    fun guess(number: Int) {
        _gameStatus.value = numberGuessGame.guess(number)
        _guessCounter.value = numberGuessGame.guessCounter
        _rangeHintMin.value = numberGuessGame.rangeHintMin
        _rangeHintMax.value = numberGuessGame.rangeHintMax
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

    private fun reset() {
        _gameStatus.value = Status.INIT
        _secretNumber.value = numberGuessGame.secretNumber
        _guessCounter.value = numberGuessGame.guessCounter
        _rangeHintMin.value = numberGuessGame.rangeHintMin
        _rangeHintMax.value = numberGuessGame.rangeHintMax
    }
}