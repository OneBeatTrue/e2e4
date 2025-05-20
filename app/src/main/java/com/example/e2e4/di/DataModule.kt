package com.example.e2e4.di

import com.example.data.repository.ChessRepositoryImpl
import com.example.data.repository.GameRepositoryImpl
import com.example.data.storage.ChessApi
import com.example.data.storage.InMemoryUserStorage
import com.example.data.storage.MockChessApi
import com.example.data.storage.UserStorage
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository
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
    fun provideGameRepository(userStorage: UserStorage) : GameRepository {
        return GameRepositoryImpl(userStorage)
    }

    @Provides
    @Singleton
    fun provideChessApi() : ChessApi {
        return MockChessApi()
    }

    @Provides
    @Singleton
    fun provideChessRepository(chessApi: ChessApi) : ChessRepository {
        return ChessRepositoryImpl(chessApi)
    }
}