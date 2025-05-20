package com.example.e2e4.presentation.screens.game

import com.example.domain.models.Board

data class GameState(
    val board: Board = Board.StartWhite,
    val chosenRow: Int = -1,
    val chosenCol: Int = -1,
)

