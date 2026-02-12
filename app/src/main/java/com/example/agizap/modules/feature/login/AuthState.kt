package com.example.agizap.modules.feature.login


sealed class AuthState {
    object Deslogado : AuthState()
    data class Logado(val uid: String?) : AuthState()
    data class Erro(val msg: String) : AuthState()
}