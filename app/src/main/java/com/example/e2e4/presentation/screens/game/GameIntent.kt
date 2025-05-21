package com.example.e2e4.presentation.screens.game

import com.example.domain.models.Board

sealed class GameIntent {
    data class Choose(val row: Int, val col: Int) : GameIntent()
    class Resign() : GameIntent()
    class Retry() : GameIntent()
    class Update(val board: Board) : GameIntent()
}