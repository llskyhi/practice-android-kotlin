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

    val minSecretNumber: LiveData<Int> = MutableLiveData(numberGuessGame.minSecretNumber)
    val maxSecretNumber: LiveData<Int> = MutableLiveData(numberGuessGame.maxSecretNumber)
    val secretNumber: LiveData<Int> = _secretNumber
    val gameStatus: LiveData<Status> = _gameStatus
    val guessCounter: LiveData<Int> = _guessCounter

    init {
        _secretNumber.value = numberGuessGame.secretNumber
        _guessCounter.value = numberGuessGame.guessCounter
    }

    fun guess(number: Int) {
        _gameStatus.value = numberGuessGame.guess(number)
        _guessCounter.value = numberGuessGame.guessCounter
    }

    fun restart() {
        numberGuessGame.reset()
        _secretNumber.value = numberGuessGame.secretNumber
        _gameStatus.value = Status.INIT
        _guessCounter.value = 0
    }
}