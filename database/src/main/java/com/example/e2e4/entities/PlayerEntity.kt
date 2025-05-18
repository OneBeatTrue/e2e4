package com.example.e2e4.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.e2e4.entities.PlayerEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PlayerEntity(
    /**
     *  Если кладешь 0 - генерируется автоматически.
     *  Работает только с Long.
     *  Если получаешь id с сервера - лучше отключить
     *  */
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String
) {
    companion object {
        const val TABLE_NAME = "player_table"
    }
}
