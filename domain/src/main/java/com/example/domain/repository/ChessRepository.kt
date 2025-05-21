package com.example.domain.repository

import com.example.domain.models.Board
import com.example.domain.models.Move
import com.example.domain.models.SideColor

interface ChessRepository {
    fun makeMove(move: Move, board: Board): Board
    fun getStartField(color: SideColor): Board
}