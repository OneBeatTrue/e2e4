package com.example.data.general.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.general.entities.UserGeneralEntity

@Dao
interface UserGeneralDao {
    @Query("SELECT * FROM user_table WHERE name = :name")
    suspend fun getByName(name: String): UserGeneralEntity?

    @Query("SELECT * FROM user_table")
    suspend fun getAll(): List<UserGeneralEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserGeneralEntity)
}