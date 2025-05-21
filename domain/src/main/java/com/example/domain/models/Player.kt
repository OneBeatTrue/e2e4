package com.example.domain.models

data class Player(val name: String = "", val wins: Int = 0, val losses: Int = 0) {
    fun isEmpty() : Boolean {
        return name.isEmpty()
    }
    companion object {
        val Empty : Player = Player()
    }
}