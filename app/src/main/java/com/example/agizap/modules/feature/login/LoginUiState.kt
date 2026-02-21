package com.example.agizap.modules.feature.login

import com.example.agizap.model.User

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val showAlert: Boolean = false,
    val message: String = "",
    val success: Boolean = false,
    val buttonEnabled: Boolean = true,
    val users: List<User> = emptyList(),
    val alertLogin: Boolean = true
)