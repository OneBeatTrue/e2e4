package com.example.domain.models

data class Board(
    val board: Map<Cell, Piece>,
    val possibleMoves: Map<Cell, Collection<Cell>>,
    val mate: SideColor,
    val fen: String
) {
    fun isFinished(): Boolean = (mate != SideColor.None)

    companion object {
        val StartWhite = Board(
            board = mapOf(
                // 1-я горизонталь (White pieces)
                Cell("1", "a") to Piece(PieceType.Rook, SideColor.White),
                Cell("1", "b") to Piece(PieceType.Knight, SideColor.White),
                Cell("1", "c") to Piece(PieceType.Bishop, SideColor.White),
                Cell("1", "d") to Piece(PieceType.Queen, SideColor.White),
                Cell("1", "e") to Piece(PieceType.King, SideColor.White),
                Cell("1", "f") to Piece(PieceType.Bishop, SideColor.White),
                Cell("1", "g") to Piece(PieceType.Knight, SideColor.White),
                Cell("1", "h") to Piece(PieceType.Rook, SideColor.White),
                // 2-я горизонталь (White pawns)
                Cell("2", "a") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "b") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "c") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "d") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "e") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "f") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "g") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "h") to Piece(PieceType.Pawn, SideColor.White),
                // 7-я горизонталь (Black pawns)
                Cell("7", "a") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "b") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "c") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "d") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "e") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "f") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "g") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "h") to Piece(PieceType.Pawn, SideColor.Black),
                // 8-я горизонталь (Black pieces)
                Cell("8", "a") to Piece(PieceType.Rook, SideColor.Black),
                Cell("8", "b") to Piece(PieceType.Knight, SideColor.Black),
                Cell("8", "c") to Piece(PieceType.Bishop, SideColor.Black),
                Cell("8", "d") to Piece(PieceType.Queen, SideColor.Black),
                Cell("8", "e") to Piece(PieceType.King, SideColor.Black),
                Cell("8", "f") to Piece(PieceType.Bishop, SideColor.Black),
                Cell("8", "g") to Piece(PieceType.Knight, SideColor.Black),
                Cell("8", "h") to Piece(PieceType.Rook, SideColor.Black),
            ),
            possibleMoves = mapOf(
                Cell("2", "a") to listOf(Cell("3", "a"), Cell("4", "a")),
                Cell("2", "b") to listOf(Cell("3", "b"), Cell("4", "b")),
                Cell("2", "c") to listOf(Cell("3", "c"), Cell("4", "c")),
                Cell("2", "d") to listOf(Cell("3", "d"), Cell("4", "d")),
                Cell("2", "e") to listOf(Cell("3", "e"), Cell("4", "e")),
                Cell("2", "f") to listOf(Cell("3", "f"), Cell("4", "f")),
                Cell("2", "g") to listOf(Cell("3", "g"), Cell("4", "g")),
                Cell("2", "h") to listOf(Cell("3", "h"), Cell("4", "h")),
            ),
            mate = SideColor.None,
            fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        )

        val StartBlack = Board(
            board = mapOf(
                // White
                Cell("1", "a") to Piece(PieceType.Rook, SideColor.White),
                Cell("1", "b") to Piece(PieceType.Knight, SideColor.White),
                Cell("1", "c") to Piece(PieceType.Bishop, SideColor.White),
                Cell("1", "d") to Piece(PieceType.Queen, SideColor.White),
                Cell("1", "e") to Piece(PieceType.King, SideColor.White),
                Cell("1", "f") to Piece(PieceType.Bishop, SideColor.White),
                Cell("1", "g") to Piece(PieceType.Knight, SideColor.White),
                Cell("1", "h") to Piece(PieceType.Rook, SideColor.White),
                Cell("2", "a") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "b") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "c") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "e") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "f") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "g") to Piece(PieceType.Pawn, SideColor.White),
                Cell("2", "h") to Piece(PieceType.Pawn, SideColor.White),
                Cell("4", "d") to Piece(PieceType.Pawn, SideColor.White),
                // Black
                Cell("7", "a") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "b") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "c") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "d") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "e") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "f") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "g") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("7", "h") to Piece(PieceType.Pawn, SideColor.Black),
                Cell("8", "a") to Piece(PieceType.Rook, SideColor.Black),
                Cell("8", "b") to Piece(PieceType.Knight, SideColor.Black),
                Cell("8", "c") to Piece(PieceType.Bishop, SideColor.Black),
                Cell("8", "d") to Piece(PieceType.Queen, SideColor.Black),
                Cell("8", "e") to Piece(PieceType.King, SideColor.Black),
                Cell("8", "f") to Piece(PieceType.Bishop, SideColor.Black),
                Cell("8", "g") to Piece(PieceType.Knight, SideColor.Black),
                Cell("8", "h") to Piece(PieceType.Rook, SideColor.Black),
            ),
            possibleMoves = mapOf(
                Cell("7", "a") to listOf(Cell("6", "a"), Cell("5", "a")),
                Cell("7", "b") to listOf(Cell("6", "b"), Cell("5", "b")),
                Cell("7", "c") to listOf(Cell("6", "c"), Cell("5", "c")),
                Cell("7", "d") to listOf(Cell("6", "d"), Cell("5", "d")),
                Cell("7", "e") to listOf(Cell("6", "e"), Cell("5", "e")),
                Cell("7", "f") to listOf(Cell("6", "f"), Cell("5", "f")),
                Cell("7", "g") to listOf(Cell("6", "g"), Cell("5", "g")),
                Cell("7", "h") to listOf(Cell("6", "h"), Cell("5", "h")),
            ),
            mate = SideColor.None,
            fen = "rnbqkbnr/pppppppp/8/8/3P4/8/PPP1PPPP/RNBQKBNR b KQkq d3 0 1"
        )
    }
}
