package com.example.e2e4.presentation.screens.leaderboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(viewModel: LeaderboardViewModel) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    viewModel.onCreate()

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
                itemsIndexed(state.players) { index, player ->
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

//@Preview(showBackground = true)
//@Composable
//fun PreviewLeaderboardScreen() {
//    LeaderboardScreen(
//        GetAllPlayersUseCase(
//            PlayerRepositoryImpl(
//                InMemoryUserStorage()
//            )
//        )
//    )
//}
