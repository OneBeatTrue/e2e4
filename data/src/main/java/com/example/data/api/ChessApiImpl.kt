package com.example.data.api

import com.example.e2e4.remote.di.BaseUrl
import com.example.data.api.dto.AllPossibleMovesRequestBody
import com.example.data.api.dto.AllPossibleMovesResponseBody
import com.example.data.api.dto.BestMoveRequestBody
import com.example.data.api.dto.BestMoveResponseBody
import com.example.data.api.dto.MakeMoveRequestBody
import com.example.data.api.dto.MakeMoveResponseBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

internal class ChessApiImpl @Inject constructor(
    private val client: HttpClient,
    @BaseUrl private val baseUrl: String
) : ChessApi {

    override suspend fun postAllPossibleMoves(body: AllPossibleMovesRequestBody): AllPossibleMovesResponseBody =
        client.post("$baseUrl/api/all-possible-moves") {
            setBody(body)
            contentType(ContentType.Application.Json)
        }.body()

    override suspend fun postBestMove(body: BestMoveRequestBody): BestMoveResponseBody =
        client.post("$baseUrl/api/best-move") {
            setBody(body)
            contentType(ContentType.Application.Json)
        }.body()

    override suspend fun postMakeMove(body: MakeMoveRequestBody): MakeMoveResponseBody =
        client.post("$baseUrl/api/make-move") {
            setBody(body)
            contentType(ContentType.Application.Json)
        }.body()
}