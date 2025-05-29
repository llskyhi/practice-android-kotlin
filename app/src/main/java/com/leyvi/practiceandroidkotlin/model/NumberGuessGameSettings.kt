package com.leyvi.practiceandroidkotlin.model

class NumberGuessGameSettings {
    companion object {
        private var _minSecretNumber: Int = 1
        private var _maxSecretNumber: Int = 100
    }

    val minSecretNumber: Int
        get() = _minSecretNumber
    val maxSecretNumber: Int
        get() = _maxSecretNumber

    fun setSecretNumberRange(
        minSecretNumberInclusive: Int,
        maxSecretNumberInclusive: Int,
    ) {
        if (minSecretNumberInclusive <= maxSecretNumberInclusive) {
            _minSecretNumber = minSecretNumberInclusive
            _maxSecretNumber = maxSecretNumberInclusive
        } else {
            throw IllegalArgumentException("Minimum secret number must be less than or equal to maximum secret number.")
        }
    }
}