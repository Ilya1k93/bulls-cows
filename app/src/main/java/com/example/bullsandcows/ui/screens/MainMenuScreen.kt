package com.example.bullsandcows.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(
    onVsComputerClick: () -> Unit,
    onVsPlayerClick: () -> Unit,
    onRulesClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onStatisticsClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Быки и коровы", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onVsComputerClick) { Text("Играть против компьютера") }
        Button(onClick = onVsPlayerClick) { Text("Режим 2 игроков") }
        Button(onClick = onRulesClick) { Text("Правила") }
        Button(onClick = onSettingsClick) { Text("Настройки") }
        Button(onClick = onStatisticsClick) { Text("Статистика") }
    }
}
