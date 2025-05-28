package com.leyvi.practiceandroidkotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame.Status

class NumberGuessGameViewModel: ViewModel() {
    private val numberGuessGame = NumberGuessGame(maxSecretNumber = 100)

    private val _secretNumber = MutableLiveData<Int>()
    private val _gameStatus = MutableLiveData<Status>()
    private val _guessCounter = MutableLiveData<Int>()
    private val _rangeHintMin = MutableLiveData<Int>(numberGuessGame.rangeHintMin)
    private val _rangeHintMax = MutableLiveData<Int>(numberGuessGame.rangeHintMax)

    val secretNumber: LiveData<Int> = _secretNumber
    val gameStatus: LiveData<Status> = _gameStatus
    val guessCounter: LiveData<Int> = _guessCounter
    val rangeHintMin: LiveData<Int> = _rangeHintMin
    val rangeHintMax: LiveData<Int> = _rangeHintMax

    init {
        reset()
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

    private fun reset() {
        _gameStatus.value = Status.INIT
        _secretNumber.value = numberGuessGame.secretNumber
        _guessCounter.value = numberGuessGame.guessCounter
        _rangeHintMin.value = numberGuessGame.rangeHintMin
        _rangeHintMax.value = numberGuessGame.rangeHintMax
    }
}