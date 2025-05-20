package com.example.data.repository

import com.example.data.storage.ChessApi
import com.example.data.storage.models.ChessApiRequest
import com.example.data.storage.models.ChessApiRequestBody
import com.example.domain.models.Board
import com.example.domain.models.Move
import com.example.domain.models.SideColor
import com.example.domain.repository.ChessRepository

class ChessRepositoryImpl(private val chessApi: ChessApi) : ChessRepository {
    override fun makeMove(move: Move, board: Board): Board {
        val request = ChessApiRequest(formChessApiRequest(move, board))
        val response = chessApi.post(request)
        return convertFenToBoard(response.body.fen).copy(mate = SideColor.random()) // TODO Remove random mate
    }

    private fun formChessApiRequest(move: Move, board: Board) : ChessApiRequestBody {
        return ChessApiRequestBody(convertBoardToFen(board), convertMoveToUci(move))
    }

    private fun convertBoardToFen(board: Board) : String {
        return "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"
    }

    private fun convertMoveToUci(move: Move) : String {
        return move.from.row + move.from.column + move.to.row + move.to.column
    }

    private fun convertFenToBoard(fen: String) : Board {
        return Board.StartWhite
    }
}