package com.example.e2e4.presentation.screens.settings

sealed class SettingsIntent {
    data object SwitchVolume : SettingsIntent()
}