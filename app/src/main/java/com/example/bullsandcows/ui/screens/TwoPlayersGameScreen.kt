package com.example.bullsandcows.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.bullsandcows.ui.components.GuessHistoryList
import com.example.bullsandcows.viewmodel.TwoPlayersUiState

@Composable
fun TwoPlayersGameScreen(
    state: TwoPlayersUiState,
    digitsLength: Int,
    onInputChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onStop: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Игрок 2 угадывает число из $digitsLength цифр")
        OutlinedTextField(
            value = state.guessInput,
            onValueChange = onInputChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = state.inputError != null,
            supportingText = { state.inputError?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onSubmit, modifier = Modifier.fillMaxWidth()) { Text("Проверить") }
        Button(onClick = onStop, modifier = Modifier.fillMaxWidth()) { Text("Завершить игру") }
        GuessHistoryList(history = state.history, modifier = Modifier.weight(1f))
    }
}
