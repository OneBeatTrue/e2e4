package com.example.data.repository

import com.example.domain.models.Player
import com.example.data.general.dao.UserGeneralDao
import com.example.data.general.entities.UserGeneralEntity
import com.example.data.local.dao.UserLocalDao
import com.example.data.mapper.toGameDomain
import com.example.data.mapper.toPlayerDomain
import com.example.data.mapper.toUserGeneralEntity
import com.example.data.mapper.toUserLocalEntity
import com.example.domain.models.Board
import com.example.domain.models.Game
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.LoginPlayerParam
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject


class GameRepositoryImpl @Inject constructor(
    private val userGeneralDao: UserGeneralDao,
    private val userLocalDao: UserLocalDao
) : GameRepository {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    override val currentGameFlow = userLocalDao.getUser().map { it?.toGameDomain() ?: Game.Empty }
        .stateIn(scope = scope, started = SharingStarted.Lazily, initialValue = Game.Empty)

    override suspend fun getPlayer(param: LoginPlayerParam): Player {
        val user = userGeneralDao.getByName(param.name) ?: return Player.Empty
        return user.toPlayerDomain()
    }

    override suspend fun getAllPlayers(): Collection<Player> {
        return userGeneralDao.getAll().map { it.toPlayerDomain() };
    }

    override suspend fun createPlayer(param: RegisterPlayerParam): Player {
        val player = Player.Empty.copy(name = param.name)
        val game = Game.Empty.copy(player = player)
        userGeneralDao.insert(game.toUserGeneralEntity())
        return player
    }

    override suspend fun updateCurrentPlayer(param: Player) {
        val user = userGeneralDao.getByName(param.name)
        if (user != null) {
            val userGeneralEntity = user.copy(name = param.name, wins = param.wins, losses = param.losses)
            userGeneralDao.insert(userGeneralEntity)
            userLocalDao.clear()
            userLocalDao.insert(userGeneralEntity.toUserLocalEntity())
        } else {
            if (currentGameFlow.value.player.isEmpty()) {
                userLocalDao.insert(
                    currentGameFlow.value.copy(
                        player = Player(Player.Empty.name, param.wins, param.losses)
                    ).toUserLocalEntity()
                )
            }
            else {
                userLocalDao.clear()
                userLocalDao.insert(
                    currentGameFlow.value.copy(
                        player = Player.Empty, board = Board.StartWhite
                    ).toUserLocalEntity()
                )
            }
        }
    }

    override suspend fun updateCurrentBoard(param: Board) {
        if (!currentGameFlow.value.player.isEmpty()) userGeneralDao.insert(currentGameFlow.value.copy(board = param).toUserGeneralEntity())
        userLocalDao.insert(currentGameFlow.value.copy(board = param).toUserLocalEntity())
    }

}