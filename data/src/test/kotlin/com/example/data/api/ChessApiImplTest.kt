package com.example.data.api

import com.example.data.api.dto.AllPossibleMovesRequestBody
import com.example.data.api.dto.BestMoveRequestBody
import com.example.data.api.dto.MakeMoveRequestBody
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.*
import org.junit.Test

class ChessApiImplTest {
    private val BASE_URL = "http://51.250.31.151"
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
    private val api = ChessApiImpl(client, BASE_URL)

    @Test
    fun `postAllPossibleMoves returns moves for start position`() = runBlocking {
        val request = AllPossibleMovesRequestBody(fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")

        val response = api.postAllPossibleMoves(request)

        val expectMoves = listOf(
            "b1c3",
            "b1a3",
            "g1h3",
            "g1f3",
            "a2a4",
            "a2a3",
            "b2b4",
            "b2b3",
            "c2c4",
            "c2c3",
            "d2d4",
            "d2d3",
            "e2e4",
            "e2e3",
            "f2f4",
            "f2f3",
            "g2g4",
            "g2g3",
            "h2h4",
            "h2h3"
        )
        assertTrue(response.moves != null)
        assertEquals(expectMoves.size, response.moves!!.size,)
        assertEquals(expectMoves.toSet(), response.moves!!.toSet())
    }

    @Test
    fun `postBestMove returns move for start position`() = runBlocking {
        val request = BestMoveRequestBody(
            fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1",
            depth = 4
        )

        val response = api.postBestMove(request)

        assertTrue(response.moves != null)
        assertTrue(response.moves!!.isNotEmpty())
    }

    @Test
    fun `postMakeMove returns new fen after move`() = runBlocking {
        val fen = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        val move = "e2e4"
        val request = MakeMoveRequestBody(fen = fen, move = move)

        val response = api.postMakeMove(request)

        assertNotEquals(fen, response.fen)

        val expectedFen = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"
        assertEquals(expectedFen, response.fen)
    }
}