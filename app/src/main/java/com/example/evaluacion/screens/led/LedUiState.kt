package com.example.evaluacion.screens.led

data class LedUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val led1: Boolean = false,
    val led2: Boolean = false,
    val led3: Boolean = false
)
