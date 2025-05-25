package com.example.e2e4.presentation.screens.game

import app.cash.turbine.test
import com.example.domain.models.*
import com.example.domain.usecase.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var resignUseCase: ResignUseCase
    private lateinit var retryUseCase: RetryUseCase
    private lateinit var makeMoveUseCase: MakeMoveUseCase
    private lateinit var getCurrentGameFlowUseCase: GetCurrentGameFlowUseCase
    private lateinit var viewModel: GameViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        resignUseCase = mockk()
        retryUseCase = mockk()
        makeMoveUseCase = mockk()
        getCurrentGameFlowUseCase = mockk()
        coEvery { getCurrentGameFlowUseCase.execute() } returns flowOf(
            Game(
                board = Board.StartWhite,
                player = Player.Empty
            )
        )
        viewModel = GameViewModel(
            resignUseCase, retryUseCase, makeMoveUseCase, getCurrentGameFlowUseCase
        )
        testDispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun updateIntent_updatesBoardAndSideInState() = runTest {
        val testBoard = Board(
            board = mapOf(Cell("1", "b") to Piece(PieceType.Queen, SideColor.White)),
            possibleMoves = emptyMap(),
            mate = SideColor.Black,
            "new_fen"
        )
        val testSide = SideColor.White

        viewModel.container.stateFlow.test {
            val initial = awaitItem()
            viewModel.onIntent(GameIntent.Update(testBoard, testSide))
            testDispatcher.scheduler.advanceUntilIdle()
            val updated = awaitItem()
            assertTrue(updated.pieces.any { map -> map.value.values.isNotEmpty() })
            assertTrue(updated.isWhite)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun retryIntent_failure_emitsNotificationSideEffect() = runTest {
        coEvery { retryUseCase.execute() } throws RuntimeException("fail")
        viewModel.container.sideEffectFlow.test {
            viewModel.onIntent(GameIntent.Retry)
            testDispatcher.scheduler.advanceUntilIdle()
            val effect = awaitItem()
            assertTrue(effect is GameSideEffect.ShowNotification)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun resignIntent_failure_emitsNotificationSideEffect() = runTest {
        coEvery { resignUseCase.execute() } throws RuntimeException("fail")
        viewModel.container.sideEffectFlow.test {
            viewModel.onIntent(GameIntent.Resign)
            testDispatcher.scheduler.advanceUntilIdle()
            val effect = awaitItem()
            assertTrue(effect is GameSideEffect.ShowNotification)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
