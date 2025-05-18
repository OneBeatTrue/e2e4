package com.example.e2e4.presentation.screens.game

import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.e2e4.presentation.screens.home.HomeIntent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(viewModel: GameViewModel) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.container.sideEffectFlow) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is GameSideEffect.ShowNotification -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Игра") }) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                ChessBoard(state = state, viewModel = viewModel)
            }
            if (state.isFinished) {
                Button(
                    onClick = { viewModel.onIntent(GameIntent.Retry()) },
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Text("Заново")
                }
            }
            else {
                Button(
                    onClick = { viewModel.onIntent(GameIntent.Resign()) },
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Text("Сдаться")
                }
            }
        }
    }
}

@Composable
fun ChessBoard(state: GameState, viewModel: GameViewModel) {
    Column {
        for (row in 0 until 8) {
            Row {
                for (col in 0 until 8) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(if (row == state.chosenRow && col == state.chosenCol) Color(173, 214, 90) else if ((row + col) % 2 == 0) Color(194, 165, 143) else Color(87, 65, 48))
                            .clickable {
                                if (!state.isFinished) viewModel.onIntent(GameIntent.Highlight(row, col))
                            }
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewGameScreen() {
//    GameScreen()
//}
