package com.example.bullsandcows.navigation

object AppDestinations {
    const val MENU = "menu"
    const val RULES = "rules"
    const val SETTINGS = "settings"
    const val VS_COMPUTER = "vs_computer"
    const val TWO_PLAYERS_SECRET = "two_players_secret"
    const val PASS_DEVICE = "pass_device"
    const val TWO_PLAYERS_GAME = "two_players_game"
    const val RESULT = "result/{mode}/{attempts}"
    const val STATS = "stats"

    fun result(mode: String, attempts: Int): String = "result/$mode/$attempts"
}
