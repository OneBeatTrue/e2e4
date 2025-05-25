package com.example.e2e4.presentation.screens.home

import androidx.compose.material3.SnackbarHostState

sealed class HomeSideEffect {
    data class ShowNotification(val message: String) : HomeSideEffect() {
        suspend fun display(snackbarHostState: SnackbarHostState) {
            snackbarHostState.showSnackbar(message)
        }
    }
}