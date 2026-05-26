package com.example.bullsandcows.domain.usecase

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ValidateGuessUseCaseTest {

    private val useCase = ValidateGuessUseCase()

    @Test
    fun `returns error when has repeated digits`() {
        val error = useCase.execute("1123", 4)
        assertEquals("Цифры должны быть уникальными", error)
    }

    @Test
    fun `returns null for valid value`() {
        val error = useCase.execute("1234", 4)
        assertNull(error)
    }
}
