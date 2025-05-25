package com.example.data.mapper


import com.example.data.general.entities.UserGeneralEntity
import com.example.data.local.entities.UserLocalEntity
import com.example.domain.models.*
import com.github.bhlangonijr.chesslib.Square as LibSquare
import com.github.bhlangonijr.chesslib.Piece as LibPiece
import com.github.bhlangonijr.chesslib.PieceType as LibPieceType
import com.github.bhlangonijr.chesslib.Side as LibSide


fun SideColor.toInt() = when (this) {
    SideColor.White -> 1
    SideColor.Black -> -1
    SideColor.None -> 0
}

fun Int.toSideColor() = when (this) {
    1 -> SideColor.White
    -1 -> SideColor.Black
    else -> SideColor.None
}

fun SideColor.toBoolean() = when (this) {
    SideColor.White -> true
    else -> false
}

fun Boolean.toSideColor() = when (this) {
    true -> SideColor.White
    false -> SideColor.Black
}

fun UserLocalEntity.toUserGeneralEntity() = UserGeneralEntity(name = name, losses = losses, wins = wins, fen = fen, moves = moves, mate = mate, side = side)

fun UserGeneralEntity.toUserLocalEntity() = UserLocalEntity(name = name, losses = losses, wins = wins, fen = fen, moves = moves, mate = mate, side = side)

fun UserLocalEntity.toPlayerDomain() = Player(name = name, losses = losses, wins = wins, side = side.toSideColor())

fun UserGeneralEntity.toPlayerDomain() = this.toUserLocalEntity().toPlayerDomain()

fun UserLocalEntity.toGameDomain() = Game(player = this.toPlayerDomain(), board = fen.fenToBoardDomain(moves = moves).copy(mate = this.mate.toSideColor()))

fun UserGeneralEntity.toGameDomain() = this.toUserLocalEntity().toGameDomain()

fun Game.toUserGeneralEntity() = UserGeneralEntity(name = player.name, wins = player.wins, losses = player.losses, fen = board.fen, moves = board.possibleMoves.toMovesString(), side = player.side.toBoolean(), mate = board.mate.toInt())

fun Game.toUserLocalEntity() = this.toUserGeneralEntity().toUserLocalEntity()

fun Move.toUci() = this.let {
    it.from.column + it.from.row + it.to.column + it.to.row
}

fun String.toMove() = this.let {
    Move(
        from = Cell(column = "${it[0]}", row = "${it[1]}"),
        to = Cell(column = "${it[2]}", row = "${it[3]}")
    )
}

fun String.toMapMoves(): Map<Cell, List<Cell>> = this.let {
    val resultMoves = mutableMapOf<Cell, MutableList<Cell>>()

    for (uci in it.split("/")) {
        val move = uci.toMove()
        resultMoves.getOrPut(move.from) { mutableListOf() }.add(move.to)
    }

    resultMoves
}

fun Map<Cell, Collection<Cell>>.toMovesString() = this.flatMap { (from, toList) ->
        toList.map { to ->
            "${from.column}${from.row}${to.column}${to.row}"
        }
    }.joinToString("/")


fun String.fenToBoardDomain(moves: String) : Board {
    val board = com.github.bhlangonijr.chesslib.Board()
    board.loadFromFen(this)

    val winner = when {
        board.isMated -> board.sideToMove.flip()
        board.isStaleMate || board.isDraw -> null
        else -> null
    }

    val mate = when (winner) {
        LibSide.WHITE -> SideColor.White
        LibSide.BLACK -> SideColor.Black
        null -> SideColor.None
    }

    val boardMap = mutableMapOf<Cell, Piece>()

    for (square in LibSquare.entries) {
        val libPiece = board.getPiece(square)
        if (libPiece != LibPiece.NONE) {
            val cell = square.toCell()
            val piece = libPiece.toDomainPiece()
            boardMap[cell] = piece
        }
    }

    return Board(board = boardMap, possibleMoves = moves.toMapMoves(), mate = mate, fen = this)
}

fun LibSquare.toCell() = Cell(row = this.name[1].toString(), column = this.name[0].lowercase())

fun LibPiece.toDomainPiece() = this.let {
    val type = when (this.pieceType) {
        LibPieceType.PAWN -> PieceType.Pawn
        LibPieceType.KNIGHT -> PieceType.Knight
        LibPieceType.BISHOP -> PieceType.Bishop
        LibPieceType.ROOK -> PieceType.Rook
        LibPieceType.QUEEN -> PieceType.Queen
        LibPieceType.KING -> PieceType.King
        else -> throw IllegalArgumentException("Unknown piece type: ${it.pieceType}")
    }

    val color = when (this.pieceSide) {
        LibSide.WHITE -> SideColor.White
        LibSide.BLACK -> SideColor.Black
        else -> SideColor.None
    }

    Piece(type, color)
}