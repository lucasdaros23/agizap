package com.example.agizap.modules.feature.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.model.User
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val prefs: PreferencesManager
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()

    fun onEmailChange(value: String) {
        _uiState.value = uiState.value.copy(
            email = value
        )
    }

    fun onUsernameChange(value: String) {
        _uiState.value = uiState.value.copy(
            username = value
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

    fun register(context: Context, email: String, password: String) {
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val user = result.user ?: return@launch

                _uiState.value = uiState.value.copy(
                    message = "Cadastro realizado com sucesso",
                    success = true
                )
                onShowAlert()

                val userData = User(
                    id = user.uid,
                    email = email,
                    name = uiState.value.username,
                )

                Firebase.firestore.collection("users")
                    .document(user.uid)
                    .set(userData)
                    .await()

                prefs.saveUser(userData)

            } catch (e: Exception) {
                val message = when (e) {
                    is FirebaseAuthWeakPasswordException -> "A senha deve ter pelo menos 6 caracteres"
                    is FirebaseAuthUserCollisionException -> "Este email já está sendo usado."
                    is FirebaseAuthInvalidCredentialsException -> "O formato do email está incorreto."
                    is FirebaseNetworkException -> "Sem conexão com a internet."
                    else -> "Erro ao criar conta."
                }
                onChangeMessage(message)
                onShowAlert()
            }
        }
    }
    fun onBackButton(action: () -> Unit){
        if (uiState.value.backEnabled){
            _uiState.value = uiState.value.copy(
                backEnabled = false
            )
            action()
            viewModelScope.launch {
                delay(1000)
                _uiState.value = uiState.value.copy(
                    backEnabled = true
                )
            }
        }
    }
}