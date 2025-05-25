package com.example.e2e4.presentation.screens.leaderboard

import app.cash.turbine.test
import com.example.domain.models.Player
import com.example.domain.models.SideColor
import com.example.domain.usecase.GetAllPlayersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LeaderboardViewModelTest {

    private lateinit var getAllPlayersUseCase: GetAllPlayersUseCase
    private lateinit var viewModel: LeaderboardViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getAllPlayersUseCase = mockk()
        viewModel = LeaderboardViewModel(getAllPlayersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun whenFetchAllPlayersSucceeds_stateUpdatesWithSortedPlayers() = runTest {
        val players = listOf(
            Player(name="Ann", wins=2, losses=1, side=SideColor.White),
            Player(name="Bob", wins=3, losses=0, side=SideColor.Black),
            Player(name="Cody", wins=2, losses=0, side=SideColor.White)
        )

        coEvery { getAllPlayersUseCase.execute() } returns players

        viewModel.container.stateFlow.test {
            assertEquals(LeaderboardState(), awaitItem())

            viewModel.onCreate()
            testDispatcher.scheduler.advanceUntilIdle()

            val state = awaitItem()
            assertEquals(
                listOf(
                    players[1],
                    players[2],
                    players[0],
                ),
                state.players
            )
        }
    }

    @Test
    fun whenFetchAllPlayersFails_stateDoesNotChange() = runTest {
        coEvery { getAllPlayersUseCase.execute() } throws RuntimeException("error")

        viewModel.container.stateFlow.test {
            assertEquals(LeaderboardState(), awaitItem())

            viewModel.onCreate()
            testDispatcher.scheduler.advanceUntilIdle()

            expectNoEvents()
        }
    }
}