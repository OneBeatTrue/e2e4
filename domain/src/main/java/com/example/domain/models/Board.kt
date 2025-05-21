package com.example.domain.models

data class Board(
    val board : Map<Cell, Piece> = emptyMap(),
    val color : SideColor = SideColor.White,
    val possibleMoves : Map<Cell, Collection<Cell>> = emptyMap(),
    val mate : SideColor = SideColor.None,
) {
    fun isFinished(): Boolean = (mate != SideColor.None)
    fun isWin(): Boolean = (isFinished() && (color == mate))

    companion object {
        val StartWhite = Board(mapOf(), SideColor.White)
        val StartBlack = Board(mapOf(), SideColor.Black)
    }
}
