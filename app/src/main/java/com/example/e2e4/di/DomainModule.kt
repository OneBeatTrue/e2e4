package com.example.e2e4.di

import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository
import com.example.domain.usecase.RegisterPlayerUseCase
import com.example.domain.usecase.GetAllPlayersUseCase
import com.example.domain.usecase.GetCurrentGameFlowUseCase
import com.example.domain.usecase.LoginPlayerUseCase
import com.example.domain.usecase.MakeMoveUseCase
import com.example.domain.usecase.ResignUseCase
import com.example.domain.usecase.RetryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideLoginUseCase(gameRepository: GameRepository) : LoginPlayerUseCase {
        return LoginPlayerUseCase(gameRepository)
    }

    @Provides
    fun provideGetAllPlayersUseCase(gameRepository: GameRepository) : GetAllPlayersUseCase {
        return GetAllPlayersUseCase(gameRepository)
    }

    @Provides
    fun provideRegisterPlayerUseCase(gameRepository: GameRepository) : RegisterPlayerUseCase {
        return RegisterPlayerUseCase(gameRepository)
    }

    @Provides
    fun provideGetCurrentGameFlowUseCase(gameRepository: GameRepository) : GetCurrentGameFlowUseCase {
        return GetCurrentGameFlowUseCase(gameRepository)
    }

    @Provides
    fun provideResignPlayerFlowUseCase(gameRepository: GameRepository) : ResignUseCase {
        return ResignUseCase(gameRepository)
    }

    @Provides
    fun provideRetryUseCase(gameRepository: GameRepository, chessRepository: ChessRepository) : RetryUseCase {
        return RetryUseCase(gameRepository, chessRepository)
    }

    @Provides
    fun provideMakeMoveUseCase(gameRepository: GameRepository, chessRepository: ChessRepository) : MakeMoveUseCase {
        return MakeMoveUseCase(gameRepository, chessRepository)
    }
}