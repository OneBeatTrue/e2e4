
import com.example.domain.models.*
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository
import com.example.domain.usecase.MakeMoveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class MakeMoveUseCaseTest {

    private lateinit var gameRepository: GameRepository
    private lateinit var chessRepository: ChessRepository
    private lateinit var useCase: MakeMoveUseCase
    private lateinit var currentGameFlow: MutableStateFlow<Game>

    val player = Player("Sergey", wins = 5, losses = 3, side = SideColor.White)
    val boardBefore = Board(emptyMap(), emptyMap(), mate = SideColor.None, fen = "")
    val startingGame = Game(player = player, board = boardBefore)

    @Before
    fun setup() {
        gameRepository = mock()
        chessRepository = mock()
        currentGameFlow = MutableStateFlow(startingGame)
        whenever(gameRepository.currentGameFlow).thenReturn(currentGameFlow)
        useCase = MakeMoveUseCase(gameRepository, chessRepository)
    }

    @Test
    fun `execute updates player with win when game is finished and player is the winner`() = runTest {
        val move = Move(Cell("2", "e"), Cell("3", "e"))
        val boardAfter = Board(emptyMap(), emptyMap(), mate = SideColor.White, fen = "")

        whenever(chessRepository.makeMove(move, boardBefore)).thenReturn(boardAfter)

        useCase.execute(move)

        val expectedWinner = player.copy(wins = player.wins + 1)
        val expectedGame = Game(player = expectedWinner, board = boardAfter)

        verify(gameRepository).updateCurrentGame(expectedGame)
        verify(gameRepository, never()).updateCurrentBoard(any())
    }

    @Test
    fun `execute updates player with loss when game is finished and player lost`() = runTest {
        val move = Move(Cell("2", "e"), Cell("3", "e"))
        val boardAfter = Board(emptyMap(), emptyMap(), mate = SideColor.Black, fen = "")

        whenever(chessRepository.makeMove(move, boardBefore)).thenReturn(boardAfter)

        useCase.execute(move)

        val expectedLoser = player.copy(losses = player.losses + 1)
        val expectedGame = Game(player = expectedLoser, board = boardAfter)

        verify(gameRepository).updateCurrentGame(expectedGame)
        verify(gameRepository, never()).updateCurrentBoard(any())
    }

    @Test
    fun `execute updates only board when game is not finished`() = runTest {
        val move = Move(Cell("2", "e"), Cell("3", "e"))
        val boardAfter = Board(emptyMap(), emptyMap(), mate = SideColor.None, fen = "")

        whenever(chessRepository.makeMove(move, boardBefore)).thenReturn(boardAfter)

        useCase.execute(move)

        verify(gameRepository).updateCurrentBoard(boardAfter)
        verify(gameRepository, never()).updateCurrentGame(any())
    }
}
