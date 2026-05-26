package com.example.bullsandcows

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.bullsandcows.data.repository.SettingsRepository
import com.example.bullsandcows.data.repository.StatisticsRepository
import com.example.bullsandcows.domain.usecase.CalculateBullsAndCowsUseCase
import com.example.bullsandcows.domain.usecase.GenerateSecretNumberUseCase
import com.example.bullsandcows.domain.usecase.ValidateGuessUseCase

private val Context.dataStore by preferencesDataStore(name = "bulls_and_cows_prefs")

class BullsAndCowsApplication : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}

class AppContainer(context: Context) {
    private val dataStore = context.dataStore

    val settingsRepository = SettingsRepository(dataStore)
    val statisticsRepository = StatisticsRepository(dataStore)

    val generateSecretNumberUseCase = GenerateSecretNumberUseCase()
    val calculateBullsAndCowsUseCase = CalculateBullsAndCowsUseCase()
    val validateGuessUseCase = ValidateGuessUseCase()
}
