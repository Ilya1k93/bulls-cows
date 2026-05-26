package com.example.bullsandcows.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bullsandcows.domain.model.GuessHistoryItem

@Composable
fun GuessHistoryList(history: List<GuessHistoryItem>, modifier: Modifier = Modifier) {
    val displayedHistory = history.asReversed()

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        itemsIndexed(displayedHistory) { index, item ->
            val attemptNumber = history.size - index
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = "#$attemptNumber: ${item.guess}")
                    Text(text = "Быки: ${item.result.bulls}, Коровы: ${item.result.cows}")
                }
            }
        }
    }
}
