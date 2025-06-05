package com.leyvi.practiceandroidkotlin.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.leyvi.practiceandroidkotlin.R
import com.leyvi.practiceandroidkotlin.databinding.RowNumberGuessGameResultBinding
import com.leyvi.practiceandroidkotlin.model.entity.NumberGuessGameResult

class NumberGuessGameResultsAdapter(
    numberGuessGameResults: List<NumberGuessGameResult>
) : RecyclerView.Adapter<NumberGuessGameResultsAdapter.NumberGuessGameResultViewHolder>() {

    class NumberGuessGameResultViewHolder(
        private val binding: RowNumberGuessGameResultBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val textSecretNumberRange = binding.textSecretNumberRange
        val textGuessCount = binding.textGuessCount
        val textTimestamp = binding.textTimestamp
    }

    private val numberGuessGameResults = ArrayList(numberGuessGameResults)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NumberGuessGameResultViewHolder {
        val binding = RowNumberGuessGameResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return NumberGuessGameResultViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NumberGuessGameResultViewHolder,
        position: Int,
    ) {
        val gameResult = numberGuessGameResults[position]
        holder.apply {
            textSecretNumberRange.text = holder.textSecretNumberRange.context.getString(
                R.string.number_guess_game_result_row_secret_number_range_inclusive,
                gameResult.minSecretNumber,
                gameResult.maxSecretNumber
            )
            textGuessCount.text = gameResult.guessCount.toString()
            textTimestamp.text = gameResult.createdAt.toString()
        }
    }

    override fun getItemCount(): Int = numberGuessGameResults.size
}