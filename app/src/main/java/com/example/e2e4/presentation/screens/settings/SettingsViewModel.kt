package com.example.e2e4.presentation.screens.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): ContainerHost<SettingsState, SettingsSideEffect>, ViewModel() {

    override val container = container<SettingsState, SettingsSideEffect>(SettingsState())

    fun onIntent(intent: SettingsIntent) = when (intent) {
        is SettingsIntent.SwitchVolume -> switchVolume()
    }

    private fun switchVolume() = intent {
        reduce {
            state.copy(
                volume = !state.volume
            )
        }
    }
}