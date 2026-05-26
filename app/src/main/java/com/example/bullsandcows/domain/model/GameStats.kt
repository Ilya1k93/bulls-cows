package com.example.bullsandcows.domain.model

data class GameStats(
    val totalGames: Int = 0,
    val wins: Int = 0,
    val bestAttempts: Int? = null
)
