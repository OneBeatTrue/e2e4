package com.example.domain.usecase

import com.example.domain.models.Board
import com.example.domain.models.Player
import com.example.domain.repository.GameRepository

class ResignUseCase(private val gameRepository: GameRepository) {
    fun execute() {
        val player = gameRepository.currentGameFlow.value.player
        val board = gameRepository.currentGameFlow.value.board
        gameRepository.updateCurrentBoard(Board(board.board, board.color, board.possibleMoves, board.color.opposite()))
        gameRepository.updateCurrentPlayer(Player(player.name, player.wins, player.losses + 1))
    }
}