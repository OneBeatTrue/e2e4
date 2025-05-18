package com.example.e2e4.presentation.screens.game

import androidx.compose.ui.graphics.Color

data class GameState(
    val isFinished: Boolean = false,
    val field: Boolean = true,
    val chosenRow: Int = -1,
    val chosenCol: Int = -1,
)

