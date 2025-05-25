package com.example.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class BestMoveRequestBody(val fen: String, val depth: Int)
