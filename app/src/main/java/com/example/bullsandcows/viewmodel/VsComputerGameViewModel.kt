package com.example.bullsandcows.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bullsandcows.data.repository.StatisticsRepository
import com.example.bullsandcows.domain.model.GameMode
import com.example.bullsandcows.domain.model.GuessHistoryItem
import com.example.bullsandcows.domain.usecase.CalculateBullsAndCowsUseCase
import com.example.bullsandcows.domain.usecase.GenerateSecretNumberUseCase
import com.example.bullsandcows.domain.usecase.ValidateGuessUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class VsComputerUiState(
    val secret: String = "",
    val input: String = "",
    val inputError: String? = null,
    val history: List<GuessHistoryItem> = emptyList(),
    val isWin: Boolean = false,
    val attempts: Int = 0
)

class VsComputerGameViewModel(
    private val generateSecretNumberUseCase: GenerateSecretNumberUseCase,
    private val calculateBullsAndCowsUseCase: CalculateBullsAndCowsUseCase,
    private val validateGuessUseCase: ValidateGuessUseCase,
    private val statisticsRepository: StatisticsRepository,
    digitsLength: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        VsComputerUiState(secret = generateSecretNumberUseCase.execute(digitsLength))
    )
    val uiState: StateFlow<VsComputerUiState> = _uiState.asStateFlow()

    fun onInputChange(value: String) {
        _uiState.update { it.copy(input = value, inputError = null) }
    }

    fun submitGuess(expectedLength: Int) {
        val current = _uiState.value
        val error = validateGuessUseCase.execute(current.input, expectedLength)
        if (error != null) {
            _uiState.update { it.copy(inputError = error) }
            return
        }

        val result = calculateBullsAndCowsUseCase.execute(current.secret, current.input)
        val newHistory = current.history + GuessHistoryItem(current.input, result)
        val isWin = result.bulls == expectedLength
        _uiState.update {
            it.copy(
                input = "",
                history = newHistory,
                attempts = newHistory.size,
                isWin = isWin
            )
        }

        if (isWin) {
            viewModelScope.launch {
                statisticsRepository.recordGame(GameMode.VS_COMPUTER, won = true, attempts = newHistory.size)
            }
        }
    }

    fun surrender() {
        viewModelScope.launch {
            statisticsRepository.recordGame(
                mode = GameMode.VS_COMPUTER,
                won = false,
                attempts = _uiState.value.history.size
            )
        }
    }

    fun playAgain(digitsLength: Int) {
        _uiState.value = VsComputerUiState(secret = generateSecretNumberUseCase.execute(digitsLength))
    }
}
