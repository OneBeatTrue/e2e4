package com.example.domain.usecase

import com.example.domain.models.CreatePlayerParam
import com.example.domain.models.Player
import com.example.domain.repository.PlayerRepository

class CreatePlayerUseCase(private val playerRepository: PlayerRepository) {
    fun execute(param: CreatePlayerParam) : Player {
        return playerRepository.createPlayer(param)
    }
}