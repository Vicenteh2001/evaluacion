package com.example.evaluacion.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlin.random.Random

// ---------------------
//  ESTADO DE AUTENTICACIÓN
// ---------------------
sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    data class Success(val message: String) : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}

// ---------------------
//  VIEWMODEL
// ---------------------
class AuthViewModel(
    // 💡 valor por defecto → así Android puede crearlo sin factory
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    // email del usuario logueado
    var loggedUserEmail by mutableStateOf<String?>(null)
        private set

    var resetEmail by mutableStateOf<String?>(null)
        private set

    private var resetCode: String? = null
    private var resetCodeExpirationMillis: Long? = null
    private val _state = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val state: StateFlow<AuthUiState> = _state

    // ---------- LOGIN ----------
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading

            val result = repo.login(email, password)

            result
                .onSuccess { token ->
                    // aquí podrías guardar el token si quieres
                    loggedUserEmail = email
                    _state.value = AuthUiState.Success("Login exitoso")
                }
                .onFailure { e ->
                    _state.value = AuthUiState.Error(
                        e.message ?: "Error al iniciar sesión"
                    )
                }
        }
    }

    // ---------- REGISTER ----------
    fun register(
        name: String,
        lastName: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading

            val result = repo.register(name, lastName, email, password)

            _state.value = result.fold(
                onSuccess = { msg -> AuthUiState.Success(msg) },
                onFailure = { e -> AuthUiState.Error(e.message ?: "Error desconocido") }
            )
        }
    }

    fun resetState() {
        _state.value = AuthUiState.Idle
    }

    fun logout() {
        loggedUserEmail = null
        _state.value = AuthUiState.Idle
    }

    // Inicia proceso de recuperación: genera código de 5 dígitos
    fun startPasswordReset(email: String) {
        viewModelScope.launch {
            _state.value = AuthUiState.Loading

            // Aquí podrías validar contra backend si el email existe.
            // Por simplicidad, asumimos que cualquier email bien formado es válido.
            if (email.isBlank()) {
                _state.value = AuthUiState.Error("Debes ingresar un correo.")
                return@launch
            }

            // Generar código de 5 dígitos
            val code = (10000 + Random.nextInt(90000)).toString()
            resetEmail = email
            resetCode = code
            resetCodeExpirationMillis = System.currentTimeMillis() + 60_000 // 1 minuto

            // En un sistema real lo enviamos por correo.
            // Aquí el mensaje dirá el código para "simular" el envío.
            _state.value = AuthUiState.Success("Código enviado a tu correo: $code (válido 1 minuto)")
        }
    }

    // Verifica el código ingresado
    fun verifyResetCode(inputCode: String): Boolean {
        val stored = resetCode
        val expiration = resetCodeExpirationMillis
        val now = System.currentTimeMillis()

        if (stored == null || expiration == null) {
            _state.value = AuthUiState.Error("No se ha generado un código todavía.")
            return false
        }

        if (now > expiration) {
            _state.value = AuthUiState.Error("El código ha expirado. Solicita uno nuevo.")
            return false
        }

        return if (stored == inputCode) {
            _state.value = AuthUiState.Success("Código verificado correctamente.")
            true
        } else {
            _state.value = AuthUiState.Error("Código incorrecto.")
            false
        }
    }

    // Cambia la contraseña (simulado en cliente)
    fun finishPasswordReset(newPassword: String) {
        viewModelScope.launch {
            if (newPassword.length < 8) {
                _state.value = AuthUiState.Error("La contraseña debe tener al menos 8 caracteres.")
                return@launch
            }

            // Aquí, en un sistema real, llamarías al backend para actualizar la contraseña.
            // Nosotros sólo simulamos éxito.
            resetCode = null
            resetCodeExpirationMillis = null

            _state.value = AuthUiState.Success("Contraseña cambiada correctamente. Ahora puedes iniciar sesión.")
        }
    }
}
