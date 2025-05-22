package com.example.domain.usecase

import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.repository.GameRepository

class RegisterPlayerUseCase(private val gameRepository: GameRepository) {
    suspend fun execute(param: RegisterPlayerParam) {
        val player = gameRepository.getPlayer(LoginPlayerParam(param.name))
        if (player.isEmpty()) gameRepository.createPlayer(param)
        gameRepository.updateCurrentPlayer(player)
    }
}