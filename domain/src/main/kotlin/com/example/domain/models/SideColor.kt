package com.example.domain.models

enum class SideColor {
    White,
    Black,
    None;

    fun opposite(): SideColor = when (this) {
        White -> Black
        Black -> White
        None -> None
    }

    companion object {
        fun random(): SideColor = listOf(White, Black, None, None, None, None, None, None).random()
    }
}