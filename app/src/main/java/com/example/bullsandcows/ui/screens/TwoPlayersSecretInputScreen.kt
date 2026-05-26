package com.example.bullsandcows.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TwoPlayersSecretInputScreen(
    input: String,
    error: String?,
    digitsLength: Int,
    onInputChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Игрок 1: введите секрет из $digitsLength цифр")
        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = error != null,
            supportingText = { error?.let { Text(it) } },
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = onSubmit, modifier = Modifier.fillMaxWidth()) { Text("Продолжить") }
    }
}
