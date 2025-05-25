package com.example.data.local.di

import android.content.Context
import com.example.data.local.dao.UserLocalDao
import com.example.data.local.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDatabaseModule {
    companion object {
        @Provides
        @Singleton
        fun provideLocalDb(
            @ApplicationContext context: Context
        ): LocalDatabase = LocalDatabase.getInstance(context)

        @Provides
        @Singleton
        fun provideUserLocalDao(
            db: LocalDatabase
        ): UserLocalDao = db.userDao()
    }
}