package com.example.domain.usecase

import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.repository.GameRepository

class RegisterPlayerUseCase(private val gameRepository: GameRepository) {
    fun execute(param: RegisterPlayerParam) {
        val player = gameRepository.createPlayer(param)
        gameRepository.updateCurrentPlayer(player)
    }
}