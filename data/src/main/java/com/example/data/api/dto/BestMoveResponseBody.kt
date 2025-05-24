package com.example.data.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class BestMoveResponseBody(val moves: List<String>)
