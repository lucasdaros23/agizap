package com.example.agizap.modules.feature.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.model.User
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RegisterViewModel(
    private val userRepo: UserRepository = UserRepository(),

    ): ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

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

    fun onButtonEnabled(){
        _uiState.value = uiState.value.copy(buttonEnabled = !uiState.value.buttonEnabled)
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    _uiState.value = uiState.value.copy(
                        message = "Cadastro realizado com sucesso",
                        success = true
                    )
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
                    onChangeMessage(when(it) {
                        is FirebaseAuthWeakPasswordException -> "A senha deve ter pelo menos 6 caracteres"
                        is FirebaseAuthUserCollisionException -> "Este email já está sendo usado."
                        is FirebaseAuthInvalidCredentialsException -> "O formato do email está incorreto."
                        is FirebaseNetworkException -> "Sem conexão com a internet."
                        else -> "Erro ao criar conta."
                    })
                    onShowAlert()
                }
        }
    }
}