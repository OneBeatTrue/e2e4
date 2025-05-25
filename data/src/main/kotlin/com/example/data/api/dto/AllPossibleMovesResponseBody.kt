package com.example.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class AllPossibleMovesResponseBody(val moves: List<String>?)
