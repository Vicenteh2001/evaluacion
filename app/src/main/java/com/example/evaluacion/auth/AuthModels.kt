package com.example.evaluacion.auth

data class LoginRequest(
    val email: String,
    val password: String
)

data class UserDto(
    val id: Int,
    val name: String,
    val email: String
)

data class LoginResponse(
    val user: UserDto,
    val token: String?
)
