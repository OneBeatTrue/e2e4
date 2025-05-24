package com.example.data.di

import com.example.data.repository.ChessRepositoryImpl
import com.example.data.repository.GameRepositoryImpl
import com.example.data.general.dao.UserGeneralDao
import com.example.data.local.dao.UserLocalDao
import com.example.domain.repository.ChessRepository
import com.example.domain.repository.GameRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun bindGameRepository(impl: GameRepositoryImpl): GameRepository

    @Binds
    @Singleton
    fun bindChessRepository(impl: ChessRepositoryImpl): ChessRepository
}