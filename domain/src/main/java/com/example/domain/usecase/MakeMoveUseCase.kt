package com.example.domain.usecase

import com.example.domain.models.Move
import com.example.domain.models.Player
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository

class MakeMoveUseCase(private val gameRepository: GameRepository, private val chessRepository: ChessRepository) {
    suspend fun execute(move: Move) {
        val board = chessRepository.makeMove(move, gameRepository.currentGameFlow.value.board)
        gameRepository.updateCurrentBoard(board)
        if (board.isFinished()) {
            val player = gameRepository.currentGameFlow.value.player
            gameRepository.updateCurrentPlayer(
                if (board.isWin()) Player(player.name, player.wins + 1, player.losses)
                else Player(player.name, player.wins, player.losses + 1)
            )
        }
    }
}