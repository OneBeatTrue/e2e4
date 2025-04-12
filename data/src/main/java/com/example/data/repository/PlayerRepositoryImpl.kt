package com.example.data.repository

import com.example.domain.models.Player
import com.example.data.storage.UserStorage
import com.example.data.storage.models.User
import com.example.data.storage.models.UserIdentifier
import com.example.domain.models.GetOrCreatePlayerParam
import com.example.domain.repository.PlayerRepository


class PlayerRepositoryImpl(private val userStorage: UserStorage) : PlayerRepository {
    override fun getPlayer(param: GetOrCreatePlayerParam): Player? {
        val user = userStorage.get(UserIdentifier(param.name)) ?: return null
        return toDomain(user)
    }

    override fun getAllPlayers(): Collection<Player> {
        return userStorage.getAll().map { toDomain(it) };
    }

    override fun createPlayer(param: GetOrCreatePlayerParam): Player {
        return toDomain(userStorage.create(UserIdentifier(param.name)))
    }

    override fun savePlayer(param: Player) {
        return userStorage.save(toStorage(param))
    }

    private fun toStorage(player: Player) : User {
        return User(player.name, player.wins, player.losses)
    }

    private fun toDomain(user: User) : Player {
        return Player(user.name, user.wins, user.losses)
    }
}