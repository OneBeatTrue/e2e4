package com.example.data.repository

import com.example.data.api.ChessApi
import com.example.data.api.dto.AllPossibleMovesResponseBody
import com.example.data.api.dto.BestMoveResponseBody
import com.example.data.api.dto.MakeMoveRequestBody
import com.example.data.api.dto.MakeMoveResponseBody
import com.example.domain.models.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*

class ChessRepositoryImplTest {

    private val chessApi: ChessApi = mock()
    private lateinit var repo: ChessRepositoryImpl

    @Before
    fun setup() {
        repo = ChessRepositoryImpl(chessApi)
    }

    @Test
    fun `makeMove returns board unchanged if finished`() = runTest {
        val board = mock<Board> {
            on { isFinished() } doReturn true
        }
        val move = mock<Move>()
        val result = repo.makeMove(move, board)
        assertEquals(board, result)
        verifyNoMoreInteractions(chessApi)
    }

    @Test
    fun makeMove_returnsBoardAfterPlayerAndBotMoves() = runTest {
        val startFen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        val playerMoveUCI = "e2e4"
        val afterPlayerMoveFen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 1"
        val botMoveUCI = "g8f6"
        val afterBotMoveFen = "rnbqkb1r/pppppppp/5n2/8/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 1 2"

        whenever(chessApi.postMakeMove(any()))
            .thenReturn(MakeMoveResponseBody(afterPlayerMoveFen))
        whenever(chessApi.postBestMove(any()))
            .thenReturn(BestMoveResponseBody(listOf(botMoveUCI)))
        whenever(chessApi.postMakeMove(MakeMoveRequestBody(afterPlayerMoveFen, botMoveUCI)))
            .thenReturn(MakeMoveResponseBody(afterBotMoveFen))
        whenever(chessApi.postAllPossibleMoves(any()))
            .thenReturn(AllPossibleMovesResponseBody(listOf(playerMoveUCI, botMoveUCI)))

        val inputBoard = mock<Board> {
            on { isFinished() } doReturn false
            on { fen } doReturn startFen
        }
        val move = Move(from = Cell(row = "2", column = "e"), to = Cell(row = "4", column = "e"))

        val result = repo.makeMove(move, inputBoard)

        assertEquals(afterBotMoveFen, result.fen)
        verify(chessApi).postMakeMove(MakeMoveRequestBody(startFen, playerMoveUCI))
        verify(chessApi).postBestMove(any())
        verify(chessApi).postMakeMove(MakeMoveRequestBody(afterPlayerMoveFen, botMoveUCI))
        verify(chessApi).postAllPossibleMoves(any())
    }

    @Test
    fun `getStartBoard returns correct board for white and black`() {
        assertEquals(Board.StartWhite, repo.getStartBoard(SideColor.White))
        assertEquals(Board.StartBlack, repo.getStartBoard(SideColor.Black))
    }
}