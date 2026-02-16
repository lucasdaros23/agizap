package com.example.agizap.modules.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Deslogado)
    val authState = _authState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _uiState.value = uiState.value.copy(
                        message = "Login realizado com sucesso",
                        success = true
                    )
                    _authState.value = AuthState.Logado(it.user?.uid)
                    onShowAlert()
                }
                .addOnFailureListener {
                    _authState.value = AuthState.Erro(it.message ?: "Erro desconhecido")
                    onChangeMessage(when (it){
                        is FirebaseAuthInvalidCredentialsException -> "Email e/ou senha incorretos"
                        is FirebaseAuthInvalidUserException -> "Usuário não encontrado"
                        is FirebaseNetworkException -> "Falha na conexão. Verifique sua internet"
                        else -> "Erro ao realizar login"
                    }
                    )
                    onShowAlert()
                }
        }
    }

    fun logout() {
        auth.signOut()
        _authState.value = AuthState.Deslogado
    }
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

    fun onChangeMessage(text: String){
        _uiState.value = uiState.value.copy(
            message = text
        )
    }

    fun onShowAlert(){
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }

    fun onButtonEnabled(){
        _uiState.value = uiState.value.copy(buttonEnabled = !uiState.value.buttonEnabled)
    }
}