package com.example.domain.usecase

import com.example.domain.repository.GameRepository
import com.example.domain.models.LoginPlayerParam

class LoginPlayerUseCase(private val gameRepository: GameRepository) {
    fun execute(param: LoginPlayerParam) : Boolean {
        val player = gameRepository.getPlayer(param)
        gameRepository.updateCurrentPlayer(player)
        return !player.isEmpty()
    }
}