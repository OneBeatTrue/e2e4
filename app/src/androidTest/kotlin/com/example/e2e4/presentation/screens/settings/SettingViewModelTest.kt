package com.example.e2e4.presentation.screens.settings

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SettingsViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SettingsViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun switchVolumeTogglesTheVolumePropertyInState() = runTest {
        viewModel.container.stateFlow.test {
            val initial = awaitItem()
            assertTrue(initial.volume)

            viewModel.onIntent(SettingsIntent.SwitchVolume)
            testDispatcher.scheduler.advanceUntilIdle()

            val toggled = awaitItem()
            assertFalse(toggled.volume)

            viewModel.onIntent(SettingsIntent.SwitchVolume)
            testDispatcher.scheduler.advanceUntilIdle()

            val toggledBack = awaitItem()
            assertTrue(toggledBack.volume)
        }
    }
}
