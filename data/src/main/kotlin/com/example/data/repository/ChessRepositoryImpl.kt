package com.example.data.repository

import com.example.data.api.ChessApi
import com.example.data.mapper.fenToBoardDomain
import com.example.data.mapper.toUci
import com.example.domain.models.Board
import com.example.domain.models.Move
import com.example.domain.models.SideColor
import com.example.domain.repository.ChessRepository
import com.example.data.api.dto.AllPossibleMovesRequestBody
import com.example.data.api.dto.BestMoveRequestBody
import com.example.data.api.dto.MakeMoveRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class ChessRepositoryImpl @Inject constructor(private val chessApi: ChessApi) : ChessRepository {
    override suspend fun makeMove(move: Move, board: Board): Board = withContext(Dispatchers.IO) {
        if (board.isFinished()) return@withContext board

        val makeMovePlayerResponseBody =
            chessApi.postMakeMove(MakeMoveRequestBody(board.fen, move.toUci()))
        val bestMoveResponseBody =
            chessApi.postBestMove(BestMoveRequestBody(makeMovePlayerResponseBody.fen, 4))
        if (!bestMoveResponseBody.moves.isNullOrEmpty()) {
            val botMove = bestMoveResponseBody.moves.random() //.also { Log.d("MY_TAG", "${it}") }
            val makeBotMoveResponseBody =
                chessApi.postMakeMove(MakeMoveRequestBody(makeMovePlayerResponseBody.fen, botMove))
            val allPossibleMovesResponseBody = chessApi.postAllPossibleMoves(
                AllPossibleMovesRequestBody(makeBotMoveResponseBody.fen)
            )
            val fen = makeBotMoveResponseBody.fen
            val moves = if (allPossibleMovesResponseBody.moves != null) allPossibleMovesResponseBody.moves.joinToString(separator = "/") else ""
            return@withContext fen.fenToBoardDomain(moves = moves)
        }

        return@withContext makeMovePlayerResponseBody.fen.fenToBoardDomain(moves = "")
    }

    override fun getStartBoard(color: SideColor): Board {
        return when (color) {
            SideColor.White -> Board.StartWhite
            else -> Board.StartBlack
        }
    }
}