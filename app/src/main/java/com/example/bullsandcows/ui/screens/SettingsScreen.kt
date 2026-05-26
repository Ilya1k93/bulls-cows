package com.example.bullsandcows.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    currentLength: Int,
    onLengthSelect: (Int) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Выберите длину числа")
        listOf(4, 6, 8).forEach { option ->
            androidx.compose.foundation.layout.Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = currentLength == option, onClick = { onLengthSelect(option) })
                Text("$option цифр")
            }
        }
        Button(onClick = onBack) { Text("Сохранить и назад") }
    }
}
