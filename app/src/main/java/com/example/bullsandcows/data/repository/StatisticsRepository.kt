package com.example.bullsandcows.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.bullsandcows.data.datastore.PreferencesKeys
import com.example.bullsandcows.domain.model.GameMode
import com.example.bullsandcows.domain.model.GameStats
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StatisticsRepository(
    private val dataStore: DataStore<Preferences>
) {
    val computerStats: Flow<GameStats> = dataStore.data.map { prefs ->
        GameStats(
            totalGames = prefs[PreferencesKeys.VS_COMPUTER_TOTAL_GAMES] ?: 0,
            wins = prefs[PreferencesKeys.VS_COMPUTER_WINS] ?: 0,
            bestAttempts = prefs[PreferencesKeys.VS_COMPUTER_BEST_ATTEMPTS]
        )
    }

    val playerStats: Flow<GameStats> = dataStore.data.map { prefs ->
        GameStats(
            totalGames = prefs[PreferencesKeys.VS_PLAYER_TOTAL_GAMES] ?: 0,
            wins = prefs[PreferencesKeys.VS_PLAYER_WINS] ?: 0,
            bestAttempts = prefs[PreferencesKeys.VS_PLAYER_BEST_ATTEMPTS]
        )
    }

    suspend fun recordGame(mode: GameMode, won: Boolean, attempts: Int) {
        dataStore.edit { prefs ->
            val (gamesKey, winsKey, bestKey) = keysByMode(mode)
            val games = prefs[gamesKey] ?: 0
            val wins = prefs[winsKey] ?: 0
            val best = prefs[bestKey]

            prefs[gamesKey] = games + 1
            if (won) {
                prefs[winsKey] = wins + 1
                val newBest = if (best == null) attempts else minOf(best, attempts)
                prefs[bestKey] = newBest
            }
        }
    }

    suspend fun reset() {
        dataStore.edit { it.clear() }
    }

    private fun keysByMode(mode: GameMode): Triple<Preferences.Key<Int>, Preferences.Key<Int>, Preferences.Key<Int>> {
        return when (mode) {
            GameMode.VS_COMPUTER -> Triple(
                PreferencesKeys.VS_COMPUTER_TOTAL_GAMES,
                PreferencesKeys.VS_COMPUTER_WINS,
                PreferencesKeys.VS_COMPUTER_BEST_ATTEMPTS
            )
            GameMode.VS_PLAYER -> Triple(
                PreferencesKeys.VS_PLAYER_TOTAL_GAMES,
                PreferencesKeys.VS_PLAYER_WINS,
                PreferencesKeys.VS_PLAYER_BEST_ATTEMPTS
            )
        }
    }
}
