package com.example.evaluacion.data.remote

import com.example.evaluacion.data.remote.dto.AuthResponse
import com.example.evaluacion.data.remote.dto.LoginRequest
import com.example.evaluacion.data.remote.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/register")
    suspend fun register(
        @Body body: RegisterRequest
    ): AuthResponse

    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): AuthResponse
}
