package com.example.agizap.modules.feature.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.modules.preferences.PreferencesManager
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
class LoginViewModel @Inject constructor(): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    fun login(context: Context, email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                _uiState.value = uiState.value.copy(
                    message = "Login realizado com sucesso",
                    success = true
                )
                onShowAlert()

                val user = result.user
                if (user != null) {
                    val prefs = PreferencesManager(context)
                    prefs.saveUserData(user.uid, user.email ?: "")
                }
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

    fun logout() {
        auth.signOut()
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