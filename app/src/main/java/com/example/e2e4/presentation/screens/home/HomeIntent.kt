package com.example.e2e4.presentation.screens.home

import com.example.domain.models.Player

sealed class HomeIntent {
    data class Login(val name: String) : HomeIntent()
    data class Register(val name: String) : HomeIntent()
    data class Update(val player: Player) : HomeIntent()
}
