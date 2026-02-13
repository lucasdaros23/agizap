package com.example.agizap.modules.feature.register

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val username: String = "",
    val showAlert: Boolean = false,
    val message: String = "",
    val success: Boolean = false,
)
