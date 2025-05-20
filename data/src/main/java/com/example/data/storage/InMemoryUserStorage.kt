package com.example.data.storage

import com.example.data.storage.models.User
import com.example.data.storage.models.UserIdentifier

class InMemoryUserStorage : UserStorage {
    private val users = mutableMapOf<String, User>(
        "kolzuk" to User("kolzuk", 10, 2, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"),
        "OneBeatTrue" to User("OneBeatTrue", 8, 4, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"),
        "DimaTivator" to User("DimaTivator", 7, 5, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"),
        "random_1" to User("random_1", 6, 3, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"),
        "random_2" to User("random_2", 6, 6, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2"),
    )

    override fun get(userIdentifier: UserIdentifier): User? {
        return users[userIdentifier.name]
    }

    override fun getAll(): Collection<User> {
        return users.values
    }

    override fun create(userIdentifier: UserIdentifier): User {
        val name = userIdentifier.name
        if (!users.contains(name)) {
            users[name] = User(name, 0, 0, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq c6 0 2")
        }
        return users[name]!!
    }

    override fun save(user: User): User {
        users[user.name] = user
        return users[user.name]!!
    }
}