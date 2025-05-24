package com.example.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class MakeMoveRequestBody(val fen: String, val move: String)
