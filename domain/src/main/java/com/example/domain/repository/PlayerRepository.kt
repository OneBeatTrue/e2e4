package com.example.domain.repository

import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.Player
import com.example.domain.models.LoginPlayerParam
import kotlinx.coroutines.flow.StateFlow

interface PlayerRepository {
    val currentPlayerFlow : StateFlow<Player>

    fun getPlayer(param: LoginPlayerParam): Player?

    fun getAllPlayers(): Collection<Player>

    fun createPlayer(param: RegisterPlayerParam): Player

    fun savePlayer(param: Player): Player

    fun loginPlayer(param: Player)
}