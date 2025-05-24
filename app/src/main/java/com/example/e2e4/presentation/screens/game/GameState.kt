package com.example.e2e4.presentation.screens.game

import com.example.domain.models.Cell

data class GameState(
    val pieces: Map<Int, Map<Int, Int>> = emptyMap(),
    val chosenRow: Int = -1,
    val chosenCol: Int = -1,
    val moves: Map<Int, Int> = emptyMap(),
    val isFinished: Boolean = false,
    val possibleMoves : Map<Cell, Collection<Cell>> = emptyMap(),
    val isWhite: Boolean = true
)

