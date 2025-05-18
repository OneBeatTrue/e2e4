package com.example.e2e4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.e2e4.dao.PlayerDao
import com.example.e2e4.entities.PlayerEntity

@Database(
    entities = [
        /**
         * Сюда нужно добавлять все модели
         * */
        PlayerEntity::class
    ],
    version = 1,
)

internal abstract class AppDatabase : RoomDatabase() {

    /**
     * Сюда можно добавлять абстрактные методы для получения dao
     * */
    abstract fun playerDao(): PlayerDao

    companion object {

        /**
         * Реализация асинхронного DoubleCheck Singleton для избежания проблем с многопоточкой
         * */

        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "app_database.db"

        fun getInstance(context: Context): AppDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) { // double check
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}
