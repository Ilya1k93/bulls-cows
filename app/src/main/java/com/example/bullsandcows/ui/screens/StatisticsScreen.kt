package com.example.bullsandcows.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bullsandcows.domain.model.GameStats

@Composable
fun StatisticsScreen(
    computerStats: GameStats,
    playerStats: GameStats,
    onReset: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatsCard(title = "Против компьютера", stats = computerStats)
        StatsCard(title = "2 игрока", stats = playerStats)
        Button(onClick = onReset) { Text("Сбросить статистику") }
        Button(onClick = onBack) { Text("Назад") }
    }
}

@Composable
private fun StatsCard(title: String, stats: GameStats) {
    Card(modifier = Modifier.padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title)
            Text("Игр: ${stats.totalGames}")
            Text("Побед: ${stats.wins}")
            Text("Лучший результат: ${stats.bestAttempts ?: "—"}")
        }
    }
}
