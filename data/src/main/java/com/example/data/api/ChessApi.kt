package com.example.data.api

import com.example.e2e4.remote.dto.AllPossibleMovesRequestBody
import com.example.e2e4.remote.dto.AllPossibleMovesResponseBody
import com.example.e2e4.remote.dto.BestMoveRequestBody
import com.example.e2e4.remote.dto.BestMoveResponseBody
import com.example.e2e4.remote.dto.MakeMoveRequestBody
import com.example.e2e4.remote.dto.MakeMoveResponseBody

interface ChessApi {
    suspend fun postAllPossibleMoves(body: AllPossibleMovesRequestBody): AllPossibleMovesResponseBody
    suspend fun postBestMove(body: BestMoveRequestBody): BestMoveResponseBody
    suspend fun postMakeMove(body: MakeMoveRequestBody): MakeMoveResponseBody
}