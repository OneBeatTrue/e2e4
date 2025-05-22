package com.example.data.api

import com.example.e2e4.remote.di.BaseUrl
import com.example.e2e4.remote.dto.AllPossibleMovesRequestBody
import com.example.e2e4.remote.dto.AllPossibleMovesResponseBody
import com.example.e2e4.remote.dto.BestMoveRequestBody
import com.example.e2e4.remote.dto.BestMoveResponseBody
import com.example.e2e4.remote.dto.MakeMoveRequestBody
import com.example.e2e4.remote.dto.MakeMoveResponseBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import javax.inject.Inject

internal class ChessApiImpl @Inject constructor(
    private val client: HttpClient,
    @BaseUrl private val baseUrl: String
) : ChessApi {

    override suspend fun postAllPossibleMoves(body: AllPossibleMovesRequestBody): AllPossibleMovesResponseBody {
        return client.post("$baseUrl/api/all-possible-moves").body()
    }

    override suspend fun postBestMove(body: BestMoveRequestBody): BestMoveResponseBody {
        return client.post("$baseUrl/api/best-move").body()
    }

    override suspend fun postMakeMove(body: MakeMoveRequestBody): MakeMoveResponseBody {
        return client.post("$baseUrl/api/make-move").body()
    }
}