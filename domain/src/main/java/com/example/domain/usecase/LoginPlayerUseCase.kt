package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository
import com.example.domain.models.LoginPlayerParam

class LoginPlayerUseCase(private val playerRepository: PlayerRepository) {
    fun execute(param: LoginPlayerParam) : Boolean {
        val player = playerRepository.getPlayer(param)
        if (player != null) playerRepository.loginPlayer(player)
        return player != null
    }
}