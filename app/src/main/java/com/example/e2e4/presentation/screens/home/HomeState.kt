package com.example.e2e4.presentation.screens.home

data class HomeState(
    val name: String = "",
    val wins: Int = 0,
    val losses: Int = 0,
    val visible: Boolean = false
)
