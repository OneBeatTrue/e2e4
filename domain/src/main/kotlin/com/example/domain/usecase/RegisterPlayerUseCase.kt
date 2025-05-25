package com.example.domain.usecase

import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.repository.GameRepository

open class RegisterPlayerUseCase(private val gameRepository: GameRepository) {
    suspend fun execute(param: RegisterPlayerParam) : Boolean {
        val player = gameRepository.getPlayer(LoginPlayerParam(param.name))
        if (!player.isEmpty()) return false
        gameRepository.createPlayer(param)
        gameRepository.updateCurrentPlayer(gameRepository.getPlayer(LoginPlayerParam(param.name)))
        return true
    }
}