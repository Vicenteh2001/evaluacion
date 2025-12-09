package com.example.evaluacion.data.remote.dto

data class LedDto(
    val id: Int,
    val name: String,
    val state: Boolean
)

data class LedUpdateRequest(
    val state: Boolean
)
