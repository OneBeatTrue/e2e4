package com.example.domain.usecase

import com.example.domain.models.*
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class RetryUseCaseTest {

    private lateinit var gameRepository: GameRepository
    private lateinit var chessRepository: ChessRepository
    private lateinit var useCase: RetryUseCase
    private lateinit var stateFlow: MutableStateFlow<Game>

    @Before
    fun setUp() {
        gameRepository = mock()
        chessRepository = mock()
        val player = Player("Test", 2, 1, SideColor.White)
        val board = Board(emptyMap(), emptyMap(), mate = SideColor.None, fen = "")
        val game = Game(player, board)
        stateFlow = MutableStateFlow(game)
        whenever(gameRepository.currentGameFlow).thenReturn(stateFlow)
        useCase = RetryUseCase(gameRepository, chessRepository)
    }

    @Test
    fun `execute switches side and sets start board for new side`() = runTest {
        val playerBefore = stateFlow.value.player
        val newSide = playerBefore.side.opposite()
        val newPlayer = playerBefore.copy(side = newSide)
        val newBoard = Board(emptyMap(), emptyMap(), mate = SideColor.None, fen = "startfen")

        whenever(chessRepository.getStartBoard(newSide)).thenReturn(newBoard)

        val expectedGame = Game(player = newPlayer, board = newBoard)

        useCase.execute()

        verify(gameRepository).updateCurrentGame(expectedGame)
    }
}
