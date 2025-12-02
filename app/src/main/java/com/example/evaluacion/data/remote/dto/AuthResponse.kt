package com.example.evaluacion.data.remote.dto

data class AuthResponse(
    val success: Boolean? = null,   // para /auth/register
    val message: String? = null,    // para mensajes de error/ok
    val token: String? = null       // para /auth/login  { token, user }
)
