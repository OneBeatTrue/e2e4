package com.example.e2e4.di

import com.example.data.storage.InMemoryUserStorage
import com.example.data.storage.UserStorage
import com.example.domain.repository.PlayerRepository
import com.example.domain.usecase.CreatePlayerUseCase
import com.example.domain.usecase.GetAllPlayersUseCase
import com.example.domain.usecase.GetPlayerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetPlayerUseCase(playerRepository: PlayerRepository) : GetPlayerUseCase {
        return GetPlayerUseCase(playerRepository)
    }

    @Provides
    fun provideGetAllPlayersUseCase(playerRepository: PlayerRepository) : GetAllPlayersUseCase {
        return GetAllPlayersUseCase(playerRepository)
    }

    @Provides
    fun provideCreatePlayerUseCase(playerRepository: PlayerRepository) : CreatePlayerUseCase {
        return CreatePlayerUseCase(playerRepository)
    }
}