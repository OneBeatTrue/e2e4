package com.example.e2e4.presentation.screens.home

sealed class HomeIntent {
    data class Login(val name: String) : HomeIntent()
    data class Register(val name: String) : HomeIntent()
}
