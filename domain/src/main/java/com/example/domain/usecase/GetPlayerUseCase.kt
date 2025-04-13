package com.example.domain.usecase

import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository
import com.example.domain.models.GetPlayerParam

class GetPlayerUseCase(private val playerRepository: PlayerRepository) {
    fun execute(param: GetPlayerParam) : Player? {
        return playerRepository.getPlayer(param)
    }
}