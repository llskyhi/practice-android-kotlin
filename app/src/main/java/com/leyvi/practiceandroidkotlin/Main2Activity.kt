package com.leyvi.practiceandroidkotlin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.leyvi.practiceandroidkotlin.databinding.ActivityMain2Binding
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame
import com.leyvi.practiceandroidkotlin.viewmodel.NumberGuessGameViewModel

class Main2Activity : AppCompatActivity() {
    private val binding: ActivityMain2Binding by lazy {
        ActivityMain2Binding.inflate(layoutInflater)
    }
    private val numberGuessGameViewModel = NumberGuessGameViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)

        numberGuessGameViewModel.guessCounter.observe(this) { counter ->
            binding.guessCounter.text = counter.toString()
        }

        numberGuessGameViewModel.gameStatus.observe(this) { status ->
            if (status == NumberGuessGame.Status.INIT) {
                return@observe
            }

            val alertDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.number_guess_game_result_title))
            when (status) {
                NumberGuessGame.Status.BINGO -> alertDialog.setMessage(getString(R.string.number_guess_result_message_bingo))
                    .setPositiveButton(getString(R.string.number_guess_restart_button)) { _, _ -> numberGuessGameViewModel.restart() }

                NumberGuessGame.Status.TOO_BIG -> alertDialog.setMessage(getString(R.string.number_guess_game_result_message_too_big))
                    .setPositiveButton(getString(R.string.alert_dialog_positive_button), null)
                    .setNeutralButton(getString(R.string.number_guess_restart_button)) { _, _ -> numberGuessGameViewModel.restart() }

                NumberGuessGame.Status.TOO_SMALL -> alertDialog.setMessage(getString(R.string.number_guess_game_result_message_too_small))
                    .setPositiveButton(getString(R.string.alert_dialog_positive_button), null)
                    .setNeutralButton(getString(R.string.number_guess_restart_button)) { _, _ -> numberGuessGameViewModel.restart() }

                else -> {}
            }
            alertDialog.show()
        }

        numberGuessGameViewModel.minSecretNumber.observe(this) { minSecretNumber ->
            val maxSecretNumber = numberGuessGameViewModel.maxSecretNumber.value
            binding.guessNumberInput.hint =
                getString(R.string.guess_number_input_hint, minSecretNumber, maxSecretNumber)
        }

        numberGuessGameViewModel.maxSecretNumber.observe(this) { maxSecretNumber ->
            val minSecretNumber = numberGuessGameViewModel.minSecretNumber.value
            binding.guessNumberInput.hint =
                getString(R.string.guess_number_input_hint, minSecretNumber, maxSecretNumber)
        }

        numberGuessGameViewModel.secretNumber.observe(this) { secretNumber ->
            Toast.makeText(
                this,
                getString(R.string.number_guess_cheating_whisper, secretNumber),
                Toast.LENGTH_SHORT
            ).show()
        }

//        binding.guessButton.setOnClickListener(this::onGuessButtonClicked)
    }

    fun onGuessButtonClicked(view: View) {
        val guessNumber = binding.guessNumberInput.text.toString().toIntOrNull()
        if (guessNumber == null) {
            Toast.makeText(this,
                getString(R.string.number_guess_game_invalid_guess_hint), Toast.LENGTH_SHORT).show()
        } else {
            numberGuessGameViewModel.guess(guessNumber)
        }
        binding.guessNumberInput.text.clear()
    }
}