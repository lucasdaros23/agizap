package com.example.agizap.modules.feature.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val showAlert: Boolean = false,
    val message: String = "",
    val success: Boolean = false
)
