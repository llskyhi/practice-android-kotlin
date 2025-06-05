package com.leyvi.practiceandroidkotlin.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.ZonedDateTime

@Entity(
    tableName = "number_guess_game_result",
)
data class NumberGuessGameResult(
    @ColumnInfo(name = "min_secret_number")
    var minSecretNumber: Int,
    @ColumnInfo(name = "max_secret_number")
    var maxSecretNumber: Int,
    @ColumnInfo(name = "guess_count")
    var guessCount: Int,
    @ColumnInfo(name = "created_at")
    var createdAt: ZonedDateTime,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
)