package com.example.e2e4.remote.api

import com.example.e2e4.remote.dto.PlayerDto

interface PlayerApi {
    suspend fun getPlayer(): PlayerDto
}