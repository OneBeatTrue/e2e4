package com.example.e2e4.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.e2e4.presentation.screens.about.AboutScreen
import com.example.e2e4.presentation.screens.game.GameScreen
import com.example.e2e4.presentation.screens.game.GameViewModel
import com.example.e2e4.presentation.screens.home.HomeScreen
import com.example.e2e4.presentation.screens.leaderboard.LeaderboardScreen
import com.example.e2e4.presentation.screens.settings.SettingsScreen
import com.example.e2e4.presentation.screens.home.HomeViewModel
import com.example.e2e4.presentation.screens.leaderboard.LeaderboardViewModel
import com.example.e2e4.presentation.screens.settings.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val gameViewModel: GameViewModel by viewModels()
    private val leaderboardViewModel: LeaderboardViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessApp()
        }
    }

    @Composable
    fun ChessApp() {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomNavigationBar(navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen(homeViewModel) }
                composable("game") { GameScreen(gameViewModel) }
                composable("leaderboard") { LeaderboardScreen(leaderboardViewModel) }
                composable("settings") { SettingsScreen(settingsViewModel) }
                composable("about") { AboutScreen() }
            }
        }
    }

    @Composable
    fun BottomNavigationBar(navController: NavController) {
        NavigationBar {
            val items = listOf(
                "home" to "Главная",
                "game" to "Игра",
                "leaderboard" to "Рейтинг",
                "settings" to "Настройки",
                "about" to "О нас"
            )
            items.forEach { (route, title) ->
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(route) },
                    label = { Text(title) },
                    icon = {}
                )
            }
        }
    }
}
