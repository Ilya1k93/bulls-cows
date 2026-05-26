package com.example.bullsandcows.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Правила") }) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Загадывается число из уникальных цифр без ведущего нуля.")
            Text("Бык — цифра стоит на правильной позиции.")
            Text("Корова — цифра есть в числе, но на другом месте.")
            Text("Победа: все цифры угаданы на своих местах.")
            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) { Text("Назад") }
        }
    }
}
