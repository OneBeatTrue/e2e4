package com.example.domain.usecase

import com.example.domain.models.Game
import com.example.domain.models.Move
import com.example.domain.models.Player
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository

class MakeMoveUseCase(private val gameRepository: GameRepository, private val chessRepository: ChessRepository) {
    suspend fun execute(move: Move) {
        val updateBoard = chessRepository.makeMove(move, gameRepository.currentGameFlow.value.board)
        if (updateBoard.isFinished()) {
            val player = gameRepository.currentGameFlow.value.player
            val updatePlayer = if (player.side == updateBoard.mate) player.copy(wins = player.wins + 1) else player.copy(losses = player.losses + 1)
            gameRepository.updateCurrentGame(Game(player = updatePlayer, board = updateBoard))
        }
        else {
            gameRepository.updateCurrentBoard(updateBoard)
        }
    }
}