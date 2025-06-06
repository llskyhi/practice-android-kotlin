package com.leyvi.practiceandroidkotlin.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "profit_center",
    indices = [
        Index(
            value = [
                "code",
            ],
            unique = true,
        ),
    ],
)
data class ProfitCenter(
    @ColumnInfo(name = "code")
    val code: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    // TODO
    // @ColumnInfo(name = "thumbnail", typeAffinity = ColumnInfo.BLOB)
    // val thumbnail: Array<Byte>?,
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
)
