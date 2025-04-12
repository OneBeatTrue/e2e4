package com.example.domain.repository

import com.example.domain.models.Player
import com.example.domain.models.GetOrCreatePlayerParam

interface PlayerRepository {
    fun getPlayer(param: GetOrCreatePlayerParam): Player?

    fun getAllPlayers(): Collection<Player>

    fun createPlayer(param: GetOrCreatePlayerParam): Player

    fun savePlayer(param: Player)
}