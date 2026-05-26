package com.example.bullsandcows.data.datastore

import androidx.datastore.preferences.core.intPreferencesKey

object PreferencesKeys {
    val DIGITS_LENGTH = intPreferencesKey("digits_length")

    val VS_COMPUTER_TOTAL_GAMES = intPreferencesKey("vs_computer_total_games")
    val VS_COMPUTER_WINS = intPreferencesKey("vs_computer_wins")
    val VS_COMPUTER_BEST_ATTEMPTS = intPreferencesKey("vs_computer_best_attempts")

    val VS_PLAYER_TOTAL_GAMES = intPreferencesKey("vs_player_total_games")
    val VS_PLAYER_WINS = intPreferencesKey("vs_player_wins")
    val VS_PLAYER_BEST_ATTEMPTS = intPreferencesKey("vs_player_best_attempts")
}
