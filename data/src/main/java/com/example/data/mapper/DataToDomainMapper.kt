package com.example.data.mapper


import com.example.data.general.entities.UserGeneralEntity
import com.example.data.local.entities.UserLocalEntity
import com.example.domain.models.Board
import com.example.domain.models.Cell
import com.example.domain.models.Game
import com.example.domain.models.Move
import com.example.domain.models.Piece
import com.example.domain.models.PieceType
import com.example.domain.models.Player
import com.example.domain.models.SideColor
import com.github.bhlangonijr.chesslib.Side
import com.github.bhlangonijr.chesslib.Square

import com.github.bhlangonijr.chesslib.Side as LibSide
import com.github.bhlangonijr.chesslib.Piece as LibPiece
import com.github.bhlangonijr.chesslib.PieceType as LibPieceType

fun UserGeneralEntity.toUserLocalEntity() = UserLocalEntity(name = name, losses = losses, wins = wins, fen = fen, moves = moves, win = win, finished = finished, side = side)

fun UserLocalEntity.toUserGeneralEntity() = UserGeneralEntity(name = name, losses = losses, wins = wins, fen = fen, moves = moves, win = win, finished = finished, side = side)

fun UserGeneralEntity.toGameDomain() = this.toUserLocalEntity().toGameDomain()

fun UserGeneralEntity.toPlayerDomain() = Player(name, wins, losses)


fun UserLocalEntity.toGameDomain() = Game(player = Player(name, wins, losses), board = fenAndMovesToBoardDomain(fen = fen, moves = moves).copy(color = if (side) SideColor.White else SideColor.Black, mate = if (!finished) SideColor.None else (if (win) (if (side) SideColor.White else SideColor.Black) else (if (side) SideColor.Black else SideColor.White))))

fun Game.toUserLocalEntity() = this.toUserGeneralEntity().toUserLocalEntity()

fun Game.toUserGeneralEntity() = UserGeneralEntity(name = player.name, wins = player.wins, losses = player.losses, fen = board.fen, moves = convertMapToMovesString(board.possibleMoves), finished = board.isFinished(), side = (board.color == SideColor.White), win = board.isWin())

fun Move.toUci() = this.let {
    it.from.column + it.from.row + it.to.column + it.to.row
}

fun String.toMove() = this.let {
    Move(
        from = Cell(column = "${it[0]}", row = "${it[1]}"),
        to = Cell(column = "${it[2]}", row = "${it[3]}")
    )
}

fun String.toMapMoves() = this.let {
    val resultMoves = mutableMapOf<Cell, MutableList<Cell>>()

    for (uci in it.split("/")) {
        val move = uci.toMove()
        resultMoves.getOrPut(move.from) { mutableListOf() }.add(move.to)
    }

    resultMoves
}

fun convertMapToMovesString(movesMap: Map<Cell, Collection<Cell>>): String {
    return movesMap.flatMap { (from, toList) ->
        toList.map { to ->
            "${from.column}${from.row}${to.column}${to.row}"
        }
    }.joinToString("\\")
}

fun fenAndMovesToBoardDomain(fen: String, moves: String) : Board {
    val board = com.github.bhlangonijr.chesslib.Board()
    board.loadFromFen(fen)

    val color = if (board.sideToMove == Side.WHITE) SideColor.White else SideColor.Black

    val winner = when {
        board.isMated -> board.sideToMove.flip()
        board.isStaleMate || board.isDraw -> null
        else -> null
    }

    val mate = when (winner) {
        Side.WHITE -> SideColor.White
        Side.BLACK -> SideColor.Black
        null -> SideColor.None
    }

    val resultBoard = mutableMapOf<Cell, Piece>()

    for (square in Square.entries) {
        val libPiece = board.getPiece(square)
        if (libPiece != LibPiece.NONE) {
            val cell = squareToCell(square)
            val piece = convertPiece(libPiece)
            resultBoard[cell] = piece
        }
    }

    val resultMoves = moves.toMapMoves()

    return Board(board = resultBoard, color = color, possibleMoves = resultMoves, mate = mate, fen = fen)
}

private fun squareToCell(square: Square): Cell {
    val name = square.name
    val column = name[0].lowercase()
    val row = name[1].toString()
    return Cell(row = row, column = column)
}

private fun convertPiece(libPiece: LibPiece): Piece {
    val type = when (libPiece.pieceType) {
        LibPieceType.PAWN -> PieceType.Pawn
        LibPieceType.KNIGHT -> PieceType.Knight
        LibPieceType.BISHOP -> PieceType.Bishop
        LibPieceType.ROOK -> PieceType.Rook
        LibPieceType.QUEEN -> PieceType.Queen
        LibPieceType.KING -> PieceType.King
        else -> throw IllegalArgumentException("Unknown piece type: ${libPiece.pieceType}")
    }

    val color = when (libPiece.pieceSide) {
        LibSide.WHITE -> SideColor.White
        LibSide.BLACK -> SideColor.Black
        else -> SideColor.None
    }

    return Piece(type, color)
}