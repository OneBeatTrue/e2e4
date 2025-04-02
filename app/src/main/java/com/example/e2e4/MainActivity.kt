package com.example.e2e4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.*

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

@Composable
fun HomeScreen(navController: NavController?) {
    Scaffold { innerPadding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val (title, textField, button) = createRefs()

            Text(
                text = "Добро пожаловать в E2E4!",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(parent.top, margin = 32.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            var text by rememberSaveable { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Пользователь") },
                modifier = Modifier.constrainAs(textField) {
                    top.linkTo(title.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Button(
                onClick = { navController?.navigate("game") },
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(textField.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            ) {
                Text("Начать игру")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen(navController = null)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen() {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Игра") }) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .size(328.dp)
                    .border(4.dp, Color.Gray, shape = RoundedCornerShape(4.dp))
                    .padding(4.dp)
            ) {
                ChessBoard()
            }
        }
    }
}

@Composable
fun ChessBoard() {
    Column {
        for (row in 0 until 8) {
            Row {
                for (col in 0 until 8) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(if ((row + col) % 2 == 0) Color.White else Color.Black)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameScreen() {
    GameScreen()
}

data class Player(val name: String, val wins: Int, val losses: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen() {
    val players = listOf(
        Player("kolzuk", 10, 2),
        Player("OneBeatTrue", 8, 4),
        Player("DimaTivator", 7, 5),
        Player("random_1", 12, 3),
        Player("random_2", 6, 6)
    )

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Рейтинг") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            LazyColumn {
                item {
                    Row(modifier = Modifier.fillMaxWidth().background(Color.LightGray).padding(8.dp)) {
                        Text("№", modifier = Modifier.width(40.dp))
                        Text("Игрок", modifier = Modifier.weight(1f))
                        Text("Победы", modifier = Modifier.width(80.dp))
                        Text("Поражения", modifier = Modifier.width(100.dp))
                    }
                }
                itemsIndexed(players) { index, player ->
                    val backgroundColor = if (index % 2 == 0) Color.White else Color.LightGray
                    Row(
                        modifier = Modifier.fillMaxWidth().background(backgroundColor).padding(8.dp)
                    ) {
                        Text("${index + 1}", modifier = Modifier.width(40.dp))
                        Text(player.name, modifier = Modifier.weight(1f))
                        Text("${player.wins}", modifier = Modifier.width(80.dp))
                        Text("${player.losses}", modifier = Modifier.width(100.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLeaderboardScreen() {
    LeaderboardScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    var soundEnabled by rememberSaveable { mutableStateOf(true)}
    val soundIcon = remember { derivedStateOf {
        if (soundEnabled) R.drawable.baseline_volume_up_24 else R.drawable.baseline_volume_off_24
    } }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Настройки") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Text("Звук")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Switch(
                    checked = soundEnabled,
                    onCheckedChange = {
                        soundEnabled = it
                    }
                )
                Icon(
                    painter = painterResource(id = soundIcon.value),
                    contentDescription = "Sound Icon"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen() {
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Создатели") }) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.authors),
                contentDescription = "Authors photo",
                modifier = Modifier.size(400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("kolzuk & OneBeatTrue", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAboutScreen() {
    AboutScreen()
}