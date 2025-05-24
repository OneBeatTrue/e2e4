package com.example.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AllPossibleMovesRequestBody(val fen: String)
