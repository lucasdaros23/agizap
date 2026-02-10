package com.example.agizap.modules.feature.login

import androidx.lifecycle.ViewModel
import com.example.agizap.modules.feature.home.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(value: String){
        _uiState.value = uiState.value.copy(
            email = value
        )
    }

    fun onPasswordChange(value: String){
        _uiState.value = uiState.value.copy(
            password = value
        )
    }
}