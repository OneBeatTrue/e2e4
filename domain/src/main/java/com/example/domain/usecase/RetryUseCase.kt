package com.example.domain.usecase

import com.example.domain.models.SideColor
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository

class RetryUseCase(
    private val gameRepository: GameRepository,
    private val chessRepository: ChessRepository
) {
    suspend fun execute() {
        val player = gameRepository.currentGameFlow.value.player
        val side = if ((player.wins + player.losses) % 2 == 0) SideColor.White else SideColor.Black
        gameRepository.updateCurrentBoard(chessRepository.getStartBoard(side))
        gameRepository.updateCurrentPlayer(player.copy(side = side))
    }
}