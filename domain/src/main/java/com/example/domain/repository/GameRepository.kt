package com.example.domain.repository

import com.example.domain.models.Board
import com.example.domain.models.Game
import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.Player
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {
    val currentGameFlow : StateFlow<Game>

    fun getPlayer(param: LoginPlayerParam): Player

    fun getAllPlayers(): Collection<Player>

    fun createPlayer(param: RegisterPlayerParam): Player

    fun updateCurrentPlayer(param: Player)

    fun updateCurrentBoard(param: Board)
}