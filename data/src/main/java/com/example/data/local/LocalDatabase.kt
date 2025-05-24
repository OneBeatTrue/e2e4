package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.UserLocalDao
import com.example.data.local.entities.UserLocalEntity


@Database(
    entities = [
        UserLocalEntity::class
    ],
    version = 1,
)

abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserLocalDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "local_database.db"

        fun getInstance(context: Context): LocalDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}
