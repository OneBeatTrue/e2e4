package com.example.e2e4.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.e2e4.entities.PlayerEntity
import kotlinx.coroutines.flow.Flow

/**
 * Методы реализует код-ген. Просто нужно прокинуть этот интерфейс в конструктор реализации репозитория
 * */
@Dao
interface PlayerDao {

    /**
     * Операции вставки/удаления/изменения должны быть suspend
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: PlayerEntity)

    /**
     * Если возвращаем flow - можем подписаться на изменения в реальном времени
     * */
    @Query("SELECT * FROM player_table WHERE id = :id")
    fun getById(id: Long): Flow<PlayerEntity>
}