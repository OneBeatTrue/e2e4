package com.example.e2e4.presentation.screens.game

import com.example.domain.models.Board
import com.example.domain.models.SideColor

sealed class GameIntent {
    data class Choose(val row: Int, val col: Int) : GameIntent()
    data object Resign : GameIntent()
    data object Retry : GameIntent()
    class Update(val board: Board, val side: SideColor) : GameIntent()
}