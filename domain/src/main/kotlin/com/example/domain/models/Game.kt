package com.example.domain.models

data class Game(
    val player: Player = Player.Empty,
    val board: Board = Board.StartWhite,
) {
    companion object {
        val Empty : Game = Game(Player.Empty, Board.StartWhite)
    }
}