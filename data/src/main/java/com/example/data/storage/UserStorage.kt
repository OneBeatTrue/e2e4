package com.example.data.storage

import com.example.data.storage.models.User
import com.example.data.storage.models.UserIdentifier

interface UserStorage {
    fun get(userIdentifier: UserIdentifier): User?

    fun getAll(): Collection<User>

    fun create(userIdentifier: UserIdentifier): User

    fun save(user: User)
}