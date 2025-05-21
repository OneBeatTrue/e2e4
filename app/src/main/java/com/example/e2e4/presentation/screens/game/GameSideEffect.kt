package com.example.e2e4.presentation.screens.game

import androidx.compose.material3.SnackbarHostState

sealed class GameSideEffect {
    data class ShowNotification(val message: String) : GameSideEffect() {
        suspend fun display(snackbarHostState: SnackbarHostState) {
            snackbarHostState.showSnackbar(message)
        }
    }
}