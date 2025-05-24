package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.UserLocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserLocalEntity)

    @Query("SELECT * FROM user_table LIMIT 1")
    fun getUser(): Flow<UserLocalEntity?>

    @Query("DELETE FROM user_table")
    fun clear()
}