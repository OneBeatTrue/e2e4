package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.repository.GameRepository

class GetAllPlayersUseCase(private val gameRepository: GameRepository) {
    fun execute() : Collection<Player> {
        return gameRepository.getAllPlayers()
    }
}