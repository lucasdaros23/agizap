package com.example.agizap.modules.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.model.User
import com.example.agizap.modules.feature.login.AuthState
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val userRepo: UserRepository = UserRepository(),

    ): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Deslogado)
    val authState = _authState.asStateFlow()

    fun onEmailChange(value: String){
        _uiState.value = uiState.value.copy(
            email = value
        )
    }

    fun onUsernameChange(value: String){
        _uiState.value = uiState.value.copy(
             username = value
        )
    }

    fun onPasswordChange(value: String){
        _uiState.value = uiState.value.copy(
            password = value
        )
    }

    fun onChangeMessage(text: String){
        _uiState.value = uiState.value.copy(
            message = text
        )
    }

    fun onShowAlert(){
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _uiState.value = uiState.value.copy(
                        message = "Cadastro realizado com sucesso",
                        success = true
                    )
                    _authState.value = AuthState.Logado(it.user?.uid)
                    userRepo.addUser(
                        User(
                            email = email,
                            name = uiState.value.username,
                            id = it.user?.uid ?: ""
                        )
                    )
                    onShowAlert()
                }
                .addOnFailureListener {
                    _authState.value = AuthState.Erro(it.message ?: "Erro desconhecido")
                    onChangeMessage("Erro ao realizar cadastro")
                    onShowAlert()
                }
        }
    }
}