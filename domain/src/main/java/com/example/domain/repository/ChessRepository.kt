package com.example.domain.repository

import com.example.domain.models.Board
import com.example.domain.models.Move

interface ChessRepository {
    fun makeMove(move: Move, board: Board): Board
}