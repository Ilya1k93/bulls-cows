package com.example.bullsandcows.domain.usecase

import kotlin.random.Random

class GenerateSecretNumberUseCase {
    fun execute(length: Int, random: Random = Random.Default): String {
        require(length in setOf(4, 6, 8)) { "Length must be 4, 6 or 8" }

        val digits = ('0'..'9').toMutableList()
        val firstDigitCandidates = digits.filter { it != '0' }
        val firstDigit = firstDigitCandidates.random(random)
        digits.remove(firstDigit)

        val result = mutableListOf(firstDigit)
        repeat(length - 1) {
            val next = digits.random(random)
            digits.remove(next)
            result += next
        }
        return result.joinToString("")
    }
}
