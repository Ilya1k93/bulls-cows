package com.example.bullsandcows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bullsandcows.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val digitsLength: StateFlow<Int> = settingsRepository.digitsLength.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = 4
    )

    fun setDigitsLength(length: Int) {
        viewModelScope.launch {
            settingsRepository.setDigitsLength(length)
        }
    }
}
