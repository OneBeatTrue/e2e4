package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository

class ResignUseCase(private val playerRepository: PlayerRepository) {
    fun execute() {
        val player = playerRepository.currentPlayerFlow.value
        playerRepository.savePlayer(Player(player.name, player.wins, player.losses + 1))
    }
}