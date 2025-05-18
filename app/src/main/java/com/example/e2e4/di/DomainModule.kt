package com.example.e2e4.di

import com.example.domain.repository.PlayerRepository
import com.example.domain.usecase.RegisterPlayerUseCase
import com.example.domain.usecase.GetAllPlayersUseCase
import com.example.domain.usecase.GetCurrentPlayerFlowUseCase
import com.example.domain.usecase.LoginPlayerUseCase
import com.example.domain.usecase.ResignUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideLoginUseCase(playerRepository: PlayerRepository) : LoginPlayerUseCase {
        return LoginPlayerUseCase(playerRepository)
    }

    @Provides
    fun provideGetAllPlayersUseCase(playerRepository: PlayerRepository) : GetAllPlayersUseCase {
        return GetAllPlayersUseCase(playerRepository)
    }

    @Provides
    fun provideRegisterPlayerUseCase(playerRepository: PlayerRepository) : RegisterPlayerUseCase {
        return RegisterPlayerUseCase(playerRepository)
    }

    @Provides
    fun provideGetCurrentPlayerFlowUseCase(playerRepository: PlayerRepository) : GetCurrentPlayerFlowUseCase {
        return GetCurrentPlayerFlowUseCase(playerRepository)
    }

    @Provides
    fun provideResignPlayerFlowUseCase(playerRepository: PlayerRepository) : ResignUseCase {
        return ResignUseCase(playerRepository)
    }
}