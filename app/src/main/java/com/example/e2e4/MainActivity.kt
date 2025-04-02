package com.example.e2e4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.e2e4.screens.AboutScreen
import com.example.e2e4.screens.GameScreen
import com.example.e2e4.screens.HomeScreen
import com.example.e2e4.screens.LeaderboardScreen
import com.example.e2e4.screens.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChessApp()
        }
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
            composable("home") { HomeScreen(navController) }
            composable("game") { GameScreen() }
            composable("leaderboard") { LeaderboardScreen() }
            composable("settings") { SettingsScreen() }
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
