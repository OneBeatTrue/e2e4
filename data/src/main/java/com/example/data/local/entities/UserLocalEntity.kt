package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.local.entities.UserLocalEntity.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class UserLocalEntity(
    @PrimaryKey val name: String,
    val wins: Int,
    val losses: Int,
    val fen: String,
    val moves: String,
    val finished: Boolean,
    val win: Boolean,
    val side: Boolean
) {
    companion object {
        const val TABLE_NAME = "user_table"
    }
}
