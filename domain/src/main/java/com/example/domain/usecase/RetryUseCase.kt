package com.example.domain.usecase

import com.example.domain.models.Board
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository

class RetryUseCase(private val gameRepository: GameRepository, private val chessRepository: ChessRepository) {
    fun execute() {
        val player = gameRepository.currentGameFlow.value.player
        val board = if ((player.wins + player.losses) % 2 == 0) Board.StartWhite else Board.StartBlack
        gameRepository.updateCurrentBoard(board)
    }
}