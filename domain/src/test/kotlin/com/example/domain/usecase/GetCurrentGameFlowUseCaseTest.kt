package com.example.domain.usecase

import com.example.domain.models.Game
import com.example.domain.models.Player
import com.example.domain.models.Board
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlinx.coroutines.flow.first

class GetCurrentGameFlowUseCaseTest {
    private lateinit var gameRepository: GameRepository
    private lateinit var useCase: GetCurrentGameFlowUseCase

    @Before
    fun setUp() {
        gameRepository = mock()
        useCase = GetCurrentGameFlowUseCase(gameRepository)
    }

    @Test
    fun `execute emits current game from repository`() = runTest {
        val expectedGame = Game(Player.Empty, Board.StartWhite)
        val stateFlow = MutableStateFlow(expectedGame)
        whenever(gameRepository.currentGameFlow).thenReturn(stateFlow)

        val flow = useCase.execute()
        val actualGame = flow.first()

        assertEquals(expectedGame, actualGame)
    }
}