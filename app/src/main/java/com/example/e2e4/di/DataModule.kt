package com.example.e2e4.di

import com.example.data.repository.PlayerRepositoryImpl
import com.example.data.storage.InMemoryUserStorage
import com.example.data.storage.UserStorage
import com.example.domain.repository.PlayerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideUserStorage() : UserStorage {
        return InMemoryUserStorage()
    }

    @Provides
    @Singleton
    fun providePlayerRepository(userStorage: UserStorage) : PlayerRepository {
        return PlayerRepositoryImpl(userStorage)
    }
}