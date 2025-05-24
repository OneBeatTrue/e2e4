package com.example.domain.repository

import com.example.domain.models.Board
import com.example.domain.models.Move
import com.example.domain.models.SideColor

interface ChessRepository {
    suspend fun makeMove(move: Move, board: Board): Board
    fun getStartBoard(color: SideColor): Board
}