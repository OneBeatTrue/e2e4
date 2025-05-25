package com.example.e2e4.presentation.screens.settings

import com.example.e2e4.presentation.screens.home.HomeIntent

sealed class SettingsIntent {
    class SwitchVolume() : SettingsIntent()
}