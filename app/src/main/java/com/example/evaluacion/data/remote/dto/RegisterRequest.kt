package com.example.evaluacion.data.remote.dto

data class RegisterRequest(
    val name: String,
    val last_name: String,
    val email: String,
    val password: String
)
