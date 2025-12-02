package com.example.evaluacion.data

import com.example.evaluacion.data.remote.AuthApi
import com.example.evaluacion.data.remote.RetrofitClient
import com.example.evaluacion.data.remote.dto.LoginRequest
import com.example.evaluacion.data.remote.dto.RegisterRequest
import retrofit2.HttpException

class AuthRepository(
    private val api: AuthApi = RetrofitClient.authApi
) {

    // ------- REGISTER -------
    suspend fun register(
        name: String,
        lastName: String,
        email: String,
        password: String
    ): Result<String> {
        return try {
            val body = RegisterRequest(
                name = name,
                last_name = lastName,
                email = email,
                password = password
            )

            val response = api.register(body)

            Result.success(response.message ?: "Registro exitoso")
        } catch (e: HttpException) {
            val msg = when (e.code()) {
                400 -> "Solicitud inválida (400)"
                404 -> "Ruta de registro no encontrada (404)"
                500 -> "Error interno del servidor (500)"
                else -> "Error HTTP ${e.code()}"
            }
            Result.failure<String>(Exception(msg))
        } catch (e: Exception) {
            Result.failure<String>(e)
        }
    }

    // --------- LOGIN ---------
    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val body = LoginRequest(email, password)
            val response = api.login(body)

            val token = response.token

            if (token.isNullOrBlank()) {
                Result.failure<String>(
                    Exception(response.message ?: "Token no recibido desde el servidor")
                )
            } else {
                Result.success(token)
            }
        } catch (e: HttpException) {
            val msg = when (e.code()) {
                400 -> "Credenciales inválidas (400)"
                404 -> "Ruta de login no encontrada (404)"
                500 -> "Error interno del servidor (500)"
                else -> "Error HTTP ${e.code()}"
            }
            Result.failure<String>(Exception(msg))
        } catch (e: Exception) {
            Result.failure<String>(e)
        }
    }
}
