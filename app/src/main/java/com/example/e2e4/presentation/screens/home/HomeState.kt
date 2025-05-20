package com.example.e2e4.presentation.screens.home

import com.example.domain.models.Player

data class HomeState(
    val player : Player = Player.Empty,
    val isPlayerVisible: Boolean = false
)
