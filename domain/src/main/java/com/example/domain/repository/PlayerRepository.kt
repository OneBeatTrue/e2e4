package com.example.domain.repository

import com.example.domain.models.CreatePlayerParam
import com.example.domain.models.Player
import com.example.domain.models.GetPlayerParam

interface PlayerRepository {
    fun getPlayer(param: GetPlayerParam): Player?

    fun getAllPlayers(): Collection<Player>

    fun createPlayer(param: CreatePlayerParam): Player

    fun savePlayer(param: Player)
}