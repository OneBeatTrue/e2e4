package com.example.data.api

import com.example.data.api.dto.AllPossibleMovesRequestBody
import com.example.data.api.dto.AllPossibleMovesResponseBody
import com.example.data.api.dto.BestMoveRequestBody
import com.example.data.api.dto.BestMoveResponseBody
import com.example.data.api.dto.MakeMoveRequestBody
import com.example.data.api.dto.MakeMoveResponseBody

interface ChessApi {
    suspend fun postAllPossibleMoves(body: AllPossibleMovesRequestBody): AllPossibleMovesResponseBody
    suspend fun postBestMove(body: BestMoveRequestBody): BestMoveResponseBody
    suspend fun postMakeMove(body: MakeMoveRequestBody): MakeMoveResponseBody
}