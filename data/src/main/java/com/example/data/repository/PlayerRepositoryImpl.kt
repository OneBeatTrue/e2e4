package com.example.data.repository

import com.example.domain.models.Player
import com.example.data.storage.UserStorage
import com.example.data.storage.models.User
import com.example.data.storage.models.UserIdentifier
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.LoginPlayerParam
import com.example.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class PlayerRepositoryImpl(private val userStorage: UserStorage) : PlayerRepository {
    private val _currentPlayerFlow = MutableStateFlow(Player())
    override val currentPlayerFlow = _currentPlayerFlow.asStateFlow()

    override fun getPlayer(param: LoginPlayerParam): Player? {
        val user = userStorage.get(UserIdentifier(param.name)) ?: return null
        return toDomain(user)
    }

    override fun getAllPlayers(): Collection<Player> {
        return userStorage.getAll().map { toDomain(it) };
    }

    override fun createPlayer(param: RegisterPlayerParam): Player {
        return toDomain(userStorage.create(UserIdentifier(param.name)))
    }

    override fun savePlayer(param: Player) : Player {
        val updatedPlayer = toDomain(userStorage.save(toStorage(param)))
        if (_currentPlayerFlow.value.name == param.name) {
            loginPlayer(updatedPlayer)
        }
        return updatedPlayer
    }

    override fun loginPlayer(param: Player) {
        _currentPlayerFlow.update { player -> player.copy(param.name, param.wins, param.losses) }
    }

    private fun toStorage(player: Player) : User {
        return User(player.name, player.wins, player.losses)
    }

    private fun toDomain(user: User) : Player {
        return Player(user.name, user.wins, user.losses)
    }
}