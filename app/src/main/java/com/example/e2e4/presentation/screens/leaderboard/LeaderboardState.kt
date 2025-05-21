package com.example.e2e4.presentation.screens.leaderboard

import com.example.domain.models.Player

data class LeaderboardState(
    val players : List<Player> = listOf<Player>()
)
