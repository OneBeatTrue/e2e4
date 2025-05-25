package com.example.domain.usecase

import com.example.domain.models.Game
import com.example.domain.models.SideColor
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository

class RetryUseCase(
    private val gameRepository: GameRepository,
    private val chessRepository: ChessRepository
) {
    suspend fun execute() {
        val newSide = gameRepository.currentGameFlow.value.player.side.opposite()
        gameRepository.updateCurrentGame(Game(player = gameRepository.currentGameFlow.value.player.copy(side = newSide), board = chessRepository.getStartBoard(newSide)))
//        gameRepository.updateCurrentBoard(chessRepository.getStartBoard(newSide))
//        gameRepository.updateCurrentPlayer(gameRepository.currentGameFlow.value.player.copy(side = newSide))
    }
}