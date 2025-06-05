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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.leyvi.practiceandroidkotlin.database.NumberGuessGameResultDatabase
import com.leyvi.practiceandroidkotlin.databinding.ActivityNumberGuessGameBinding
import com.leyvi.practiceandroidkotlin.game.NumberGuessGame
import com.leyvi.practiceandroidkotlin.repo.NumberGuessGameResultRepo
import com.leyvi.practiceandroidkotlin.view.NumberGuessGameResultsAdapter
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
    private val numberGuessGameResultRepo by lazy {
        NumberGuessGameResultRepo(
            NumberGuessGameResultDatabase.getInstance(this)
                .numberGuessGameResultDao(),
        )
    }
    private val numberGuessGameViewModel by lazy {
        ViewModelProvider(
            this,
            NumberGuessGameViewModel.NumberGuessGameViewModelFactory(
                numberGuessGameResultRepo,
            )
        )[NumberGuessGameViewModel::class.java]
    }

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

        numberGuessGameViewModel.gameState.observe(this) { gameState ->
            binding.guessCounter.text = gameState.guessCounter.toString()

            if (gameState.gameStatus != NumberGuessGame.Status.INIT) {
                val alertDialog = AlertDialog.Builder(this@NumberGuessGameActivity)
                    .setTitle(getString(R.string.number_guess_game_result_title))
                    .setCancelable(false)
                when (gameState.gameStatus) {
                    NumberGuessGame.Status.BINGO -> {
                        numberGuessGameViewModel.recordGameResult()
                        alertDialog.setMessage(getString(R.string.number_guess_result_message_bingo))
                            .setPositiveButton(getString(R.string.number_guess_restart_button)) { _, _ -> numberGuessGameViewModel.restart() }
                    }

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

            binding.guessNumberInput.hint = getString(
                R.string.guess_number_input_hint,
                gameState.rangeHintMin,
                gameState.rangeHintMax,
            )
        }

        numberGuessGameViewModel.secretNumber.observe(this) { secretNumber ->
            Toast.makeText(
                this,
                getString(R.string.number_guess_cheating_whisper, secretNumber),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.recyclerGameResults.layoutManager = LinearLayoutManager(this)
        numberGuessGameViewModel.gameResults.observe(this) { gameResults ->
            Log.d(TAG, "onCreate: game results updated: $gameResults")
            binding.recyclerGameResults.adapter = NumberGuessGameResultsAdapter(gameResults)
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