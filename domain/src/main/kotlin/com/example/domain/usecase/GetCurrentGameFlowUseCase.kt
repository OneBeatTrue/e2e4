package com.example.domain.usecase

import com.example.domain.models.Game
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

open class GetCurrentGameFlowUseCase(private val gameRepository: GameRepository) {
    fun execute() : Flow<Game> {
        return gameRepository.currentGameFlow
    }
}