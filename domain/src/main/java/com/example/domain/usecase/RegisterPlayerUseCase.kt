package com.example.domain.usecase

import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository

class RegisterPlayerUseCase(private val playerRepository: PlayerRepository) {
    fun execute(param: RegisterPlayerParam) {
        val player = playerRepository.createPlayer(param)
        playerRepository.loginPlayer(player)
    }
}