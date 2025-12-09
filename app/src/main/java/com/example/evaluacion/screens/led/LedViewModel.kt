package com.example.evaluacion.screens.led

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion.data.remote.LedRepository
import com.example.evaluacion.data.remote.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LedViewModel : ViewModel() {

    private val repository = LedRepository(RetrofitClient.ledApi)

    private val _uiState = MutableStateFlow(LedUiState(isLoading = true))
    val uiState: StateFlow<LedUiState> = _uiState

    init {
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                loadLeds()
                delay(2000) // cada 2 segundos
            }
        }
    }

    private suspend fun loadLeds() {
        try {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            val leds = repository.getLeds()

            var led1 = false
            var led2 = false
            var led3 = false

            leds.forEach { led ->
                when (led.id) {
                    1 -> led1 = led.state
                    2 -> led2 = led.state
                    3 -> led3 = led.state
                }
            }

            _uiState.value = LedUiState(
                isLoading = false,
                led1 = led1,
                led2 = led2,
                led3 = led3
            )

        } catch (e: Exception) {
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                error = e.message ?: "Error al cargar LEDs"
            )
        }
    }

    fun onToggleLed(id: Int, newState: Boolean) {
        viewModelScope.launch {
            try {
                repository.updateLed(id, newState)
                val current = _uiState.value
                _uiState.value = when (id) {
                    1 -> current.copy(led1 = newState)
                    2 -> current.copy(led2 = newState)
                    3 -> current.copy(led3 = newState)
                    else -> current
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Error al actualizar LED $id"
                )
            }
        }
    }
}
