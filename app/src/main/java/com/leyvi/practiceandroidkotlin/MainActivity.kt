package com.leyvi.practiceandroidkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.leyvi.practiceandroidkotlin.databinding.ActivityAppEntryBinding

class MainActivity : ComponentActivity() {
    private val binding by lazy {
        ActivityAppEntryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(binding.root)
    }

    // TODO: change to use Navigation?
    fun onNavButtonClick(view: View) {
        when(view) {
            binding.buttonNavToNumberGuessGame -> Intent(this, NumberGuessGameActivity::class.java)
            binding.buttonNavToProfitCenterCrud -> Intent(this, ProfitCenterCrudActivity::class.java)
            else -> throw IllegalArgumentException("Unknown view: $view")
        }.also {
            startActivity(it)
        }
    }
}
