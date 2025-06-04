package com.leyvi.practice.androidkotlin

data class TodoItem(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean,
)