package com.example.bullsandcows.domain.usecase

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GenerateSecretNumberUseCaseTest {

    private val useCase = GenerateSecretNumberUseCase()

    @Test
    fun `generated number has required length and unique digits`() {
        val secret = useCase.execute(6, Random(42))

        assertEquals(6, secret.length)
        assertTrue(secret.first() != '0')
        assertEquals(secret.length, secret.toSet().size)
        assertTrue(secret.all { it.isDigit() })
    }
}
