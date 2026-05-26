package com.example.bullsandcows.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.bullsandcows.data.datastore.PreferencesKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    val digitsLength: Flow<Int> = dataStore.data.map { it[PreferencesKeys.DIGITS_LENGTH] ?: 4 }

    suspend fun setDigitsLength(length: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DIGITS_LENGTH] = length
        }
    }
}
