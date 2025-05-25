package com.example.domain.usecase

import com.example.domain.models.*
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class ResignUseCaseTest {

    private lateinit var gameRepository: GameRepository
    private lateinit var useCase: ResignUseCase
    private lateinit var stateFlow: MutableStateFlow<Game>

    @Before
    fun setUp() {
        gameRepository = mock()
        val player = Player("Test", 5, 2, SideColor.Black)
        val board = Board(emptyMap(), emptyMap(), mate = SideColor.None, fen = "")
        val game = Game(player = player, board = board)
        stateFlow = MutableStateFlow(game)
        whenever(gameRepository.currentGameFlow).thenReturn(stateFlow)
        useCase = ResignUseCase(gameRepository)
    }

    @Test
    fun `execute adds loss and sets mate to opposite side`() = runTest {
        val playerBefore = stateFlow.value.player
        val boardBefore = stateFlow.value.board
        val expectedPlayer = playerBefore.copy(losses = playerBefore.losses + 1)
        val expectedMate = playerBefore.side.opposite()
        val expectedBoard = boardBefore.copy(mate = expectedMate)
        val expectedGame = Game(player = expectedPlayer, board = expectedBoard)

        useCase.execute()

        verify(gameRepository).updateCurrentGame(expectedGame)
    }
}
