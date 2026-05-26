package com.example.bullsandcows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bullsandcows.navigation.AppDestinations
import com.example.bullsandcows.ui.screens.MainMenuScreen
import com.example.bullsandcows.ui.screens.PassDeviceScreen
import com.example.bullsandcows.ui.screens.ResultScreen
import com.example.bullsandcows.ui.screens.RulesScreen
import com.example.bullsandcows.ui.screens.SettingsScreen
import com.example.bullsandcows.ui.screens.StatisticsScreen
import com.example.bullsandcows.ui.screens.TwoPlayersGameScreen
import com.example.bullsandcows.ui.screens.TwoPlayersSecretInputScreen
import com.example.bullsandcows.ui.screens.VsComputerGameScreen
import com.example.bullsandcows.ui.theme.BullsAndCowsTheme
import com.example.bullsandcows.viewmodel.SettingsViewModel
import com.example.bullsandcows.viewmodel.StatisticsViewModel
import com.example.bullsandcows.viewmodel.TwoPlayersGameViewModel
import com.example.bullsandcows.viewmodel.VsComputerGameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as BullsAndCowsApplication
        val container = app.appContainer

        setContent {
            BullsAndCowsTheme {
                val navController = rememberNavController()

                val settingsViewModel: SettingsViewModel = viewModel(factory = simpleFactory {
                    SettingsViewModel(container.settingsRepository)
                })
                val statsViewModel: StatisticsViewModel = viewModel(factory = simpleFactory {
                    StatisticsViewModel(container.statisticsRepository)
                })
                val digitsLength by settingsViewModel.digitsLength.collectAsStateWithLifecycle()

                val vsComputerViewModel: VsComputerGameViewModel = viewModel(factory = simpleFactory {
                    VsComputerGameViewModel(
                        generateSecretNumberUseCase = container.generateSecretNumberUseCase,
                        calculateBullsAndCowsUseCase = container.calculateBullsAndCowsUseCase,
                        validateGuessUseCase = container.validateGuessUseCase,
                        statisticsRepository = container.statisticsRepository,
                        digitsLength = digitsLength
                    )
                })

                val twoPlayersViewModel: TwoPlayersGameViewModel = viewModel(factory = simpleFactory {
                    TwoPlayersGameViewModel(
                        calculateBullsAndCowsUseCase = container.calculateBullsAndCowsUseCase,
                        validateGuessUseCase = container.validateGuessUseCase,
                        statisticsRepository = container.statisticsRepository
                    )
                })

                val vsState by vsComputerViewModel.uiState.collectAsStateWithLifecycle()
                val twoPlayersState by twoPlayersViewModel.uiState.collectAsStateWithLifecycle()
                val statsState by statsViewModel.uiState.collectAsStateWithLifecycle()

                NavHost(navController = navController, startDestination = AppDestinations.MENU) {
                    composable(AppDestinations.MENU) {
                        MainMenuScreen(
                            onVsComputerClick = {
                                vsComputerViewModel.playAgain(digitsLength)
                                navController.navigate(AppDestinations.VS_COMPUTER)
                            },
                            onVsPlayerClick = {
                                twoPlayersViewModel.reset()
                                navController.navigate(AppDestinations.TWO_PLAYERS_SECRET)
                            },
                            onRulesClick = { navController.navigate(AppDestinations.RULES) },
                            onSettingsClick = { navController.navigate(AppDestinations.SETTINGS) },
                            onStatisticsClick = { navController.navigate(AppDestinations.STATS) }
                        )
                    }

                    composable(AppDestinations.RULES) {
                        RulesScreen(onBack = { navController.popBackStack() })
                    }

                    composable(AppDestinations.SETTINGS) {
                        SettingsScreen(
                            currentLength = digitsLength,
                            onLengthSelect = settingsViewModel::setDigitsLength,
                            onBack = { navController.popBackStack() }
                        )
                    }

                    composable(AppDestinations.VS_COMPUTER) {
                        VsComputerGameScreen(
                            state = vsState,
                            digitsLength = digitsLength,
                            onInputChange = vsComputerViewModel::onInputChange,
                            onSubmit = {
                                vsComputerViewModel.submitGuess(digitsLength)
                                if (vsComputerViewModel.uiState.value.isWin) {
                                    navController.navigate(
                                        AppDestinations.result("computer_win", vsComputerViewModel.uiState.value.attempts)
                                    )
                                }
                            },
                            onSurrender = {
                                vsComputerViewModel.surrender()
                                navController.navigate(AppDestinations.result("computer_loss", vsState.attempts))
                            }
                        )
                    }

                    composable(AppDestinations.TWO_PLAYERS_SECRET) {
                        TwoPlayersSecretInputScreen(
                            input = twoPlayersState.secretInput,
                            error = twoPlayersState.inputError,
                            digitsLength = digitsLength,
                            onInputChange = twoPlayersViewModel::onSecretInputChange,
                            onSubmit = {
                                if (twoPlayersViewModel.submitSecret(digitsLength)) {
                                    navController.navigate(AppDestinations.PASS_DEVICE)
                                }
                            }
                        )
                    }

                    composable(AppDestinations.PASS_DEVICE) {
                        PassDeviceScreen(onContinue = { navController.navigate(AppDestinations.TWO_PLAYERS_GAME) })
                    }

                    composable(AppDestinations.TWO_PLAYERS_GAME) {
                        TwoPlayersGameScreen(
                            state = twoPlayersState,
                            digitsLength = digitsLength,
                            onInputChange = twoPlayersViewModel::onGuessInputChange,
                            onSubmit = {
                                val isWin = twoPlayersViewModel.submitGuess(digitsLength)
                                if (isWin) {
                                    navController.navigate(
                                        AppDestinations.result("player_win", twoPlayersViewModel.uiState.value.history.size)
                                    )
                                }
                            },
                            onStop = {
                                twoPlayersViewModel.markLoss()
                                navController.navigate(
                                    AppDestinations.result("player_loss", twoPlayersViewModel.uiState.value.history.size)
                                )
                            }
                        )
                    }

                    composable(
                        route = AppDestinations.RESULT,
                        arguments = listOf(
                            navArgument("mode") { type = NavType.StringType },
                            navArgument("attempts") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val mode = backStackEntry.arguments?.getString("mode").orEmpty()
                        val attempts = backStackEntry.arguments?.getInt("attempts") ?: 0
                        val title = when (mode) {
                            "computer_win", "player_win" -> "Победа!"
                            else -> "Игра завершена"
                        }
                        ResultScreen(
                            title = title,
                            attempts = attempts,
                            onPlayAgain = {
                                if (mode.startsWith("computer")) {
                                    vsComputerViewModel.playAgain(digitsLength)
                                    navController.navigate(AppDestinations.VS_COMPUTER) {
                                        popUpTo(AppDestinations.MENU)
                                    }
                                } else {
                                    twoPlayersViewModel.reset()
                                    navController.navigate(AppDestinations.TWO_PLAYERS_SECRET) {
                                        popUpTo(AppDestinations.MENU)
                                    }
                                }
                            },
                            onMenu = {
                                navController.navigate(AppDestinations.MENU) {
                                    popUpTo(AppDestinations.MENU) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable(AppDestinations.STATS) {
                        StatisticsScreen(
                            computerStats = statsState.computer,
                            playerStats = statsState.player,
                            onReset = statsViewModel::resetStats,
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}

private fun <T : ViewModel> simpleFactory(create: () -> T): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <VM : ViewModel> create(modelClass: Class<VM>): VM = create() as VM
    }
}
