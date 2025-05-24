package com.example.domain.repository

import com.example.domain.models.Board
import com.example.domain.models.Game
import com.example.domain.models.LoginPlayerParam
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.Player
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {
    val currentGameFlow : StateFlow<Game>

    suspend fun getPlayer(param: LoginPlayerParam): Player

    suspend fun getAllPlayers(): Collection<Player>

    suspend fun createPlayer(param: RegisterPlayerParam): Player

    suspend fun updateCurrentPlayer(param: Player)

    suspend fun updateCurrentBoard(param: Board)

    suspend fun updateCurrentGame(param: Game)
}