package com.example.bullsandcows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bullsandcows.data.repository.StatisticsRepository
import com.example.bullsandcows.domain.model.GameStats
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class StatisticsUiState(
    val computer: GameStats = GameStats(),
    val player: GameStats = GameStats()
)

class StatisticsViewModel(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {
    val uiState: StateFlow<StatisticsUiState> = combine(
        statisticsRepository.computerStats,
        statisticsRepository.playerStats
    ) { computer, player ->
        StatisticsUiState(computer = computer, player = player)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = StatisticsUiState()
    )

    fun resetStats() {
        viewModelScope.launch { statisticsRepository.reset() }
    }
}
