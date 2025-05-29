package com.leyvi.practiceandroidkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.leyvi.practiceandroidkotlin.databinding.ActivityNumberGuessGameBinding
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame
import com.leyvi.practiceandroidkotlin.viewmodel.NumberGuessGameViewModel

class NumberGuessGameActivity : AppCompatActivity() {
    companion object {
        const val INTENT_EXTRA_MIN_SECRET_NUMBER = "minSecretNumber"
        const val INTENT_EXTRA_MAX_SECRET_NUMBER = "maxSecretNumber"
    }

    private val TAG: String = this::class.java.simpleName

    private val binding: ActivityNumberGuessGameBinding by lazy {
        ActivityNumberGuessGameBinding.inflate(layoutInflater)
    }
    private val numberGuessGameViewModel = NumberGuessGameViewModel()

    private val setNumberRangeActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.d(TAG, "onCreate: activityResult code: ${activityResult.resultCode}")
            if (activityResult.resultCode != RESULT_OK) {
                return@registerForActivityResult
            }
            val minSecretNumber =
                activityResult.data?.getIntExtra(INTENT_EXTRA_MIN_SECRET_NUMBER, 1)
            val maxSecretNumber =
                activityResult.data?.getIntExtra(INTENT_EXTRA_MAX_SECRET_NUMBER, 100)
            Log.d(
                TAG,
                "onCreate: minSecretNumber: $minSecretNumber, maxSecretNumber: $maxSecretNumber"
            )
            if (minSecretNumber == null || maxSecretNumber == null) {
                Log.w(TAG, "onCreate: received invalid range from activity result")
                return@registerForActivityResult
            }
            numberGuessGameViewModel.setRange(minSecretNumber, maxSecretNumber)
        }

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

        numberGuessGameViewModel.rangeHintMin.observe(this) { minSecretNumber ->
            val maxSecretNumber = numberGuessGameViewModel.rangeHintMax.value
            binding.guessNumberInput.hint =
                getString(R.string.guess_number_input_hint, minSecretNumber, maxSecretNumber)
        }

        numberGuessGameViewModel.rangeHintMax.observe(this) { maxSecretNumber ->
            val minSecretNumber = numberGuessGameViewModel.rangeHintMin.value
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
    }

    fun onGuessButtonClicked(view: View) {
        val guessNumber = binding.guessNumberInput.text.toString().toIntOrNull()
        if (guessNumber == null) {
            Toast.makeText(
                this,
                getString(R.string.number_guess_game_invalid_guess_hint), Toast.LENGTH_SHORT
            ).show()
        } else {
            numberGuessGameViewModel.guess(guessNumber)
        }
        binding.guessNumberInput.text.clear()
    }

    fun onSettingsButtonClicked(view: View) {
        Log.d(
            TAG, "onSettingsButtonClicked: going to configure settings: ${
                mapOf(
                    INTENT_EXTRA_MIN_SECRET_NUMBER to numberGuessGameViewModel.minSecretNumber,
                    INTENT_EXTRA_MAX_SECRET_NUMBER to numberGuessGameViewModel.maxSecretNumber,
                )
            }"
        )
        Intent(this, NumberGuessGameSettingActivity::class.java).apply {
            putExtra(INTENT_EXTRA_MIN_SECRET_NUMBER, numberGuessGameViewModel.minSecretNumber)
            putExtra(INTENT_EXTRA_MAX_SECRET_NUMBER, numberGuessGameViewModel.maxSecretNumber)
        }.also {
            setNumberRangeActivityResultLauncher.launch(it)
        }
    }
}