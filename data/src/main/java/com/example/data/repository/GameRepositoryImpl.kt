package com.example.data.repository

import com.example.domain.models.Player
import com.example.data.storage.UserStorage
import com.example.data.storage.models.User
import com.example.data.storage.models.UserIdentifier
import com.example.domain.models.Board
import com.example.domain.models.Game
import com.example.domain.models.RegisterPlayerParam
import com.example.domain.models.LoginPlayerParam
import com.example.domain.repository.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameRepositoryImpl(private val userStorage: UserStorage) : GameRepository {
    private val _currentGameFlow = MutableStateFlow(Game.Empty)
    override val currentGameFlow = _currentGameFlow.asStateFlow()

    override fun getPlayer(param: LoginPlayerParam): Player {
        val user = userStorage.get(UserIdentifier(param.name)) ?: return Player.Empty
        return toDomain(user).player
    }

    override fun getAllPlayers(): Collection<Player> {
        return userStorage.getAll().map { toDomain(it).player };
    }

    override fun createPlayer(param: RegisterPlayerParam): Player {
        return toDomain(userStorage.create(UserIdentifier(param.name))).player
    }

    override fun updateCurrentPlayer(param: Player) {
        val user = userStorage.get(UserIdentifier(param.name))
//        if (!_currentGameFlow.value.player.isEmpty()) userStorage.save(toStorage(_currentGameFlow.value))
        if (user != null) {
            val field = fenToField(user.fen)
            userStorage.save(toStorage(Game(param, field)))
            _currentGameFlow.update { game -> game.copy(player = param, board = field) }
        }
        else {
            if (_currentGameFlow.value.player.isEmpty()) _currentGameFlow.update { game -> game.copy(player = Player(Player.Empty.name, param.wins, param.losses)) }
        }
    }

    override fun updateCurrentBoard(param: Board) {
        if (!_currentGameFlow.value.player.isEmpty()) userStorage.save(toStorage(Game(_currentGameFlow.value.player, param)))
        _currentGameFlow.update { game -> game.copy(board = param) }
    }

    private fun toStorage(game: Game) : User {
        return User(game.player.name, game.player.wins, game.player.losses, fieldToFen(game.board))
    }

    private fun toDomain(user: User) : Game {
        return Game(Player(user.name, user.wins, user.losses), fenToField(user.fen))
    }

    private fun fenToField(fen: String) : Board {
        return Board.StartWhite
    }

    private fun fieldToFen(board: Board) : String {
        return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
    }
}