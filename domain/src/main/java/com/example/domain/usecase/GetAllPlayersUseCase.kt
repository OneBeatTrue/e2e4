package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository

class GetAllPlayersUseCase(private val playerRepository: PlayerRepository) {
    fun execute() : Collection<Player> {
        return playerRepository.getAllPlayers()
    }
}