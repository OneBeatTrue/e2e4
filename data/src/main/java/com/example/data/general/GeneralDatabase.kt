package com.example.data.general

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.general.dao.UserGeneralDao
import com.example.data.general.entities.UserGeneralEntity


@Database(
    entities = [
        UserGeneralEntity::class
    ],
    version = 1,
)

abstract class GeneralDatabase : RoomDatabase() {
    abstract fun userDao(): UserGeneralDao

    companion object {
        @Volatile
        private var INSTANCE: GeneralDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "general_database.db"

        fun getInstance(context: Context): GeneralDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    GeneralDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }

}
