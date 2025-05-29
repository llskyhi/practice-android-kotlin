package com.leyvi.practiceandroidkotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.leyvi.practiceandroidkotlin.databinding.ActivityNumberGuessGameSettingsBinding

class NumberGuessGameSettingActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNumberGuessGameSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.minSecretNumberEditText.setText(intent.getIntExtra(NumberGuessGameActivity.INTENT_EXTRA_MIN_SECRET_NUMBER, 1).toString())
        binding.maxSecretNumberEditText.setText(intent.getIntExtra(NumberGuessGameActivity.INTENT_EXTRA_MAX_SECRET_NUMBER, 100).toString())
    }

    fun onSaveButtonClicked(view :View) {
        val minSecretNumber = binding.minSecretNumberEditText.text.toString().toIntOrNull()
        val maxSecretNumber = binding.maxSecretNumberEditText.text.toString().toIntOrNull()

        if (minSecretNumber == null || maxSecretNumber == null || minSecretNumber > maxSecretNumber) {
            Toast.makeText(this,
                getString(R.string.number_guess_game_invalid_settings), Toast.LENGTH_SHORT).show()
            return
        }

        intent.apply {
            putExtra(NumberGuessGameActivity.INTENT_EXTRA_MIN_SECRET_NUMBER, minSecretNumber)
            putExtra(NumberGuessGameActivity.INTENT_EXTRA_MAX_SECRET_NUMBER, maxSecretNumber)
        }.also {
            setResult(RESULT_OK, it)
        }
        finish()
    }
}