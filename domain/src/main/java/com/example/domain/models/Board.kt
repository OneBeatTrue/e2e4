package com.example.domain.models

data class Board(
    val board : Map<Int, Map<Int, String>> = mapOf(),
    val color : SideColor = SideColor.White,
    val possibleMoves : Map<Int, Map<Int, String>> = mapOf(),
    val mate : SideColor = SideColor.None,
) {
    fun isFinished(): Boolean = (mate != SideColor.None)
    fun isWin(): Boolean = (isFinished() && (color == mate))

    companion object {
        val StartWhite = Board(mapOf(), SideColor.White)
        val StartBlack = Board(mapOf(), SideColor.Black)
    }
}
