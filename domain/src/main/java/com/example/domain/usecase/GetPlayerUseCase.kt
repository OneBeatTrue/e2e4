package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository
import com.example.domain.models.GetOrCreatePlayerParam

class GetPlayerUseCase(private val playerRepository: PlayerRepository) {
    fun execute(param: GetOrCreatePlayerParam) : Player {
        return playerRepository.getPlayer(param) ?: playerRepository.createPlayer(param)
    }
}