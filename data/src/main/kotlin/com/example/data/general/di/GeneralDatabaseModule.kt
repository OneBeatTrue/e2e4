package com.example.data.general.di

import android.content.Context
import com.example.data.general.GeneralDatabase
import com.example.data.general.dao.UserGeneralDao
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
interface GeneralDatabaseModule {
    companion object {
        @Provides
        @Singleton
        fun provideLocalDb(
            @ApplicationContext context: Context
        ): GeneralDatabase = GeneralDatabase.getInstance(context)

        @Provides
        @Singleton
        fun provideUserGeneralDao(
            db: GeneralDatabase
        ): UserGeneralDao = db.userDao()
    }
}