package com.example.bullsandcows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bullsandcows.data.repository.StatisticsRepository
import com.example.bullsandcows.domain.model.GameMode
import com.example.bullsandcows.domain.model.GuessHistoryItem
import com.example.bullsandcows.domain.usecase.CalculateBullsAndCowsUseCase
import com.example.bullsandcows.domain.usecase.ValidateGuessUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TwoPlayersUiState(
    val secretInput: String = "",
    val secret: String = "",
    val guessInput: String = "",
    val inputError: String? = null,
    val history: List<GuessHistoryItem> = emptyList(),
    val isWin: Boolean = false
)

class TwoPlayersGameViewModel(
    private val calculateBullsAndCowsUseCase: CalculateBullsAndCowsUseCase,
    private val validateGuessUseCase: ValidateGuessUseCase,
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TwoPlayersUiState())
    val uiState: StateFlow<TwoPlayersUiState> = _uiState.asStateFlow()

    fun onSecretInputChange(value: String) {
        _uiState.update { it.copy(secretInput = value, inputError = null) }
    }

    fun submitSecret(length: Int): Boolean {
        val state = _uiState.value
        val error = validateGuessUseCase.execute(state.secretInput, length)
        if (error != null) {
            _uiState.update { it.copy(inputError = error) }
            return false
        }
        _uiState.update { it.copy(secret = state.secretInput, secretInput = "", inputError = null) }
        return true
    }

    fun onGuessInputChange(value: String) {
        _uiState.update { it.copy(guessInput = value, inputError = null) }
    }

    fun submitGuess(length: Int): Boolean {
        val state = _uiState.value
        val error = validateGuessUseCase.execute(state.guessInput, length)
        if (error != null) {
            _uiState.update { it.copy(inputError = error) }
            return false
        }

        val result = calculateBullsAndCowsUseCase.execute(state.secret, state.guessInput)
        val newHistory = state.history + GuessHistoryItem(state.guessInput, result)
        val isWin = result.bulls == length

        _uiState.update {
            it.copy(
                guessInput = "",
                history = newHistory,
                isWin = isWin
            )
        }

        if (isWin) {
            viewModelScope.launch {
                statisticsRepository.recordGame(GameMode.VS_PLAYER, won = true, attempts = newHistory.size)
            }
        }
        return isWin
    }

    fun markLoss() {
        viewModelScope.launch {
            statisticsRepository.recordGame(GameMode.VS_PLAYER, won = false, attempts = _uiState.value.history.size)
        }
    }

    fun reset() {
        _uiState.value = TwoPlayersUiState()
    }
}
