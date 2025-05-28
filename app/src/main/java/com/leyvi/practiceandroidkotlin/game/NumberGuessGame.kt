package com.leyvi.practiceandroidkotlin.game

import kotlin.properties.Delegates

class NumberGuessGame(
    val minSecretNumber: Int = 1,
    val maxSecretNumber: Int = 10,
) : ResettableGame() {
    enum class Status {
        INIT,
        TOO_BIG,
        TOO_SMALL,
        BINGO,
    }

    var secretNumber: Int by Delegates.notNull()
        private set
    var guessCounter: Int by Delegates.notNull()
        private set

    init {
        reset()
    }

    override fun reset() {
        secretNumber = generateSecretNumber()
        guessCounter = 0
    }

    fun guess(number: Int): Status {
        guessCounter++
        val gameStatus = when {
            number > secretNumber -> Status.TOO_BIG
            number < secretNumber -> Status.TOO_SMALL
            else -> Status.BINGO
        }
        return gameStatus
    }

    private fun generateSecretNumber(): Int = (minSecretNumber..maxSecretNumber).random()
}
