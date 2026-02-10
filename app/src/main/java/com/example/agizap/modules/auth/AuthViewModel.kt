package com.example.agizap.modules.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.agizap.modules.navigation.Routes
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel(){
    private val auth = FirebaseAuth.getInstance()

    private val _state = MutableStateFlow<AuthState>(AuthState.Deslogado)
    val state = _state.asStateFlow()

    fun login(email: String, senha: String, navController: NavHostController) {
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener {
                    _state.value = AuthState.Logado(it.user?.uid)
                    navController.navigate(Routes.HOME)
                }
                .addOnFailureListener {
                    _state.value = AuthState.Erro(it.message ?: "Erro desconhecido")
                }
        }
    }

    fun register(email: String, senha: String, navController: NavHostController) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener {
                    _state.value = AuthState.Logado(it.user?.uid)
                    navController.navigate(Routes.HOME)
                }
                .addOnFailureListener {
                    _state.value = AuthState.Erro(it.message ?: "Erro desconhecido")
                }
        }
    }

    fun logout() {
        auth.signOut()
        _state.value = AuthState.Deslogado
    }
}

sealed class AuthState {
    object Deslogado : AuthState()
    data class Logado(val uid: String?) : AuthState()
    data class Erro(val msg: String) : AuthState()
}