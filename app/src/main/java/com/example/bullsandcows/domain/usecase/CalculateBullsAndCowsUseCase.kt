package com.example.bullsandcows.domain.usecase

import com.example.bullsandcows.domain.model.GuessResult

class CalculateBullsAndCowsUseCase {
    fun execute(secret: String, guess: String): GuessResult {
        require(secret.length == guess.length) { "Secret and guess must have same length" }

        val bulls = secret.indices.count { secret[it] == guess[it] }
        val cows = guess.count { it in secret } - bulls
        return GuessResult(bulls = bulls, cows = cows)
    }
}
