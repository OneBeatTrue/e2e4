package com.example.e2e4.presentation.screens.game

import com.example.e2e4.presentation.screens.home.HomeIntent

sealed class GameIntent {
    data class Highlight(val row: Int, val col: Int) : GameIntent()
    class Resign() : GameIntent()
    class Retry() : GameIntent()
}