package com.example.data.storage

import com.example.data.storage.models.User
import com.example.data.storage.models.UserIdentifier

class InMemoryUserStorage : UserStorage {
    private val users = mutableMapOf<String, User>(
        "kolzuk" to User("kolzuk", 10, 2),
        "OneBeatTrue" to User("OneBeatTrue", 8, 4),
        "DimaTivator" to User("DimaTivator", 7, 5),
        "random_1" to User("random_1", 6, 3),
        "random_2" to User("random_2", 6, 6)
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
            users[name] = User(name, 0, 0)
        }
        return users[name]!!
    }

    override fun save(user: User): User {
        users[user.name] = user
        return users[user.name]!!
    }
}