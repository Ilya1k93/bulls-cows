package com.example.bullsandcows.domain.usecase

class ValidateGuessUseCase {
    fun execute(input: String, expectedLength: Int): String? {
        if (input.length != expectedLength) {
            return "Введите число длиной $expectedLength"
        }
        if (!input.all { it.isDigit() }) {
            return "Разрешены только цифры"
        }
        if (input.firstOrNull() == '0') {
            return "Первый символ не может быть 0"
        }
        if (input.toSet().size != input.length) {
            return "Цифры должны быть уникальными"
        }
        return null
    }
}
