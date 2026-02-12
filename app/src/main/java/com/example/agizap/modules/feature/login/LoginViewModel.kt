package com.example.agizap.modules.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.agizap.modules.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Deslogado)
    val authState = _authState.asStateFlow()

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener {
                    _uiState.value = uiState.value.copy(
                        message = "Login realizado com sucesso",
                        success = true
                    )
                    _authState.value = AuthState.Logado(it.user?.uid)
                    onShowAlertLogin()
                }
                .addOnFailureListener {
                    _authState.value = AuthState.Erro(it.message ?: "Erro desconhecido")
                    onChangeMessage("Erro ao realizar login")
                    onShowAlertLogin()
                }
        }
    }

    fun register(email: String, senha: String, navController: NavHostController) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener {
                    _authState.value = AuthState.Logado(it.user?.uid)
                    navController.navigate(Routes.HOME)
                }
                .addOnFailureListener {
                    _authState.value = AuthState.Erro(it.message ?: "Erro desconhecido")
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

    fun onShowAlertLogin(){
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }
}