package com.leyvi.practiceandroidkotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.leyvi.practiceandroidkotlin.model.NumberGuessGameSettings

class NumberGuessGameSettingsViewModel : ViewModel() {
    private val numberGuessGameSettings = NumberGuessGameSettings()

    val minSecretNumber: Int
        get() = numberGuessGameSettings.minSecretNumber
    val maxSecretNumber: Int
        get() = numberGuessGameSettings.maxSecretNumber

    fun setSecretNumberRange(minSecretNumberInclusive: Int, maxSecretNumberInclusive: Int) {
        numberGuessGameSettings.setSecretNumberRange(
            minSecretNumberInclusive,
            maxSecretNumberInclusive,
        )
    }
}