package com.example.domain.models

data class Player(val name: String, val wins: Int, val losses: Int, val side: SideColor) {
    fun isEmpty() : Boolean {
        return name.isEmpty()
    }
    companion object {
        val Empty : Player = Player("", 0, 0, SideColor.White)
    }
}