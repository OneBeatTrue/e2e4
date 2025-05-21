package com.example.e2e4.remote.api

import com.example.e2e4.remote.di.BaseUrl
import com.example.e2e4.remote.dto.PlayerDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

internal class PlayerApiImpl @Inject constructor(
    private val client: HttpClient,
    @BaseUrl private val baseUrl: String
) : PlayerApi {

    /**
     * Пример метода получения игрока
     * */
    override suspend fun getPlayer(): PlayerDto {
        return client.get("$baseUrl/players/...").body()
    }

}