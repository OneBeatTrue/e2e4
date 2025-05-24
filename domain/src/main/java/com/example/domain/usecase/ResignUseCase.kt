package com.example.domain.usecase

import com.example.domain.models.Game
import com.example.domain.models.Player
import com.example.domain.repository.GameRepository

class ResignUseCase(private val gameRepository: GameRepository) {
    suspend fun execute() {
        val player = gameRepository.currentGameFlow.value.player
        gameRepository.updateCurrentGame(Game(player = gameRepository.currentGameFlow.value.player.copy(losses = player.losses + 1), board = gameRepository.currentGameFlow.value.board.copy(mate = player.side.opposite())))
//        gameRepository.updateCurrentBoard(gameRepository.currentGameFlow.value.board.copy(mate = player.side.opposite()))
//        gameRepository.updateCurrentPlayer(Player(player.name, player.wins, player.losses + 1, player.side))
    }
}