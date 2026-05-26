package com.example.bullsandcows.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals

class CalculateBullsAndCowsUseCaseTest {

    private val useCase = CalculateBullsAndCowsUseCase()

    @Test
    fun `returns correct bulls and cows`() {
        val result = useCase.execute(secret = "1234", guess = "1243")

        assertEquals(2, result.bulls)
        assertEquals(2, result.cows)
    }
}
