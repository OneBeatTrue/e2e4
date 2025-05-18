package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow

class GetCurrentPlayerFlowUseCase(private val playerRepository: PlayerRepository) {
    fun execute() : Flow<Player> {
        return playerRepository.currentPlayerFlow
    }
}