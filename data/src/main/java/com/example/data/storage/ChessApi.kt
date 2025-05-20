package com.example.data.storage

import com.example.data.storage.models.ChessApiRequest
import com.example.data.storage.models.ChessApiResponse
import com.example.data.storage.models.User
import com.example.data.storage.models.UserIdentifier

interface ChessApi {
    fun post(request: ChessApiRequest): ChessApiResponse
}