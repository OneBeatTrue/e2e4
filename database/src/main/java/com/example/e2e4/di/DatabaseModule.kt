package com.example.e2e4.di

import android.content.Context
import com.example.e2e4.AppDatabase
import com.example.e2e4.dao.PlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseModule {

    companion object {
        @Provides
        @Singleton
        fun provideDb(
            @ApplicationContext context: Context
        ): AppDatabase = AppDatabase.getInstance(context)

        @Provides
        @Singleton
        fun providePlayerDao(
            db: AppDatabase
        ): PlayerDao = db.playerDao()
    }
}