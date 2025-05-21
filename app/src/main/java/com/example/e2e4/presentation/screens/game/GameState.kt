package com.example.e2e4.presentation.screens.game

data class GameState(
    val pieces: Map<Int, Map<Int, String>> = emptyMap(),
    val chosenRow: Int = -1,
    val chosenCol: Int = -1,
    val moves: Map<Int, Int> = emptyMap(),
    val isFinished: Boolean = false,
)

