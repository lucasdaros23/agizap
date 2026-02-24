package com.example.agizap.modules.feature.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.model.User
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val auth: FirebaseAuth,
    private val prefs: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUsers()
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val user = uiState.value.users.find { it.id == result.user?.uid }
                if (user != null) {
                    if (user.active) {
                        _uiState.value = uiState.value.copy(
                            message = "Login realizado com sucesso",
                            success = true
                        )
                        val prefs = prefs

                        prefs.saveUser(user)
                    } else {
                        _uiState.value = uiState.value.copy(
                            message = "Erro ao realizar login, usuário desativado",
                        )
                    }
                }
                onShowAlert()
            }
            .addOnFailureListener { e ->
                val msg = when (e) {
                    is FirebaseAuthInvalidCredentialsException -> "Email e/ou senha incorretos"
                    is FirebaseAuthInvalidUserException -> "Usuário não encontrado"
                    is FirebaseNetworkException -> "Falha na conexão. Verifique sua internet"
                    else -> "Erro ao realizar login"
                }
                onChangeMessage(msg)
                onShowAlert()
            }
    }


    fun observeUsers() {
        viewModelScope.launch {
            userRepo.listenUsers().collect {
                _uiState.value = _uiState.value.copy(users = it)
            }
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            val users = userRepo.getUsers()
            if (users.isNotEmpty()) _uiState.value = uiState.value.copy(users = users)
        }
    }

    fun onEmailChange(value: String) {
        _uiState.value = uiState.value.copy(
            email = value
        )
    }

    fun onPasswordChange(value: String) {
        _uiState.value = uiState.value.copy(
            password = value
        )
    }

    fun onChangeMessage(text: String) {
        _uiState.value = uiState.value.copy(
            message = text
        )
    }

    fun onShowAlert() {
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }

    fun onButtonEnabled() {
        _uiState.value = uiState.value.copy(buttonEnabled = !uiState.value.buttonEnabled)
    }

    fun setAlertLogin(value: Boolean) {
        _uiState.value = uiState.value.copy(alertLogin = value)
    }
}