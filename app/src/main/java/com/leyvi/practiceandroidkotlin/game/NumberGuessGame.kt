package com.leyvi.practiceandroidkotlin.game

import kotlin.math.max
import kotlin.math.min
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
    var rangeHintMin: Int by Delegates.notNull()
        private set
    var rangeHintMax: Int by Delegates.notNull()
        private set

    init {
        reset()
    }

    override fun reset() {
        secretNumber = generateSecretNumber()
        guessCounter = 0
        rangeHintMin = minSecretNumber
        rangeHintMax = maxSecretNumber
    }

    fun guess(number: Int): Status {
        guessCounter++
        when {
            number > secretNumber -> {
                rangeHintMax = min(number - 1, rangeHintMax)
                return Status.TOO_BIG
            }
            number < secretNumber -> {
                rangeHintMin = max(number + 1, rangeHintMin)
                return Status.TOO_SMALL
            }
            else -> {
                // not updating range hints, regarding it's meaningless
                return Status.BINGO
            }
        }
    }

    private fun generateSecretNumber(): Int = (minSecretNumber..maxSecretNumber).random()
}
