package com.example.agizap.modules.feature.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.agizap.model.User
import com.example.agizap.modules.navigation.Routes
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val userRepo: UserRepository,
    private val auth: FirebaseAuth,
    private val prefs: PreferencesManager,

    ) : ViewModel() {
    private val _uiState = MutableStateFlow(EditUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUser() {
        val user = prefs.getUser() ?: User()
        _uiState.value = uiState.value.copy(
            currentUser = user,
            nameTextField = user.name,
            newPhoto = user.photo
        )
    }

    init{
        observeUser()
    }

    private fun observeUser() {
        val id = prefs.getUser()?.id ?: return
        viewModelScope.launch {
            userRepo.listenUser(id).collect { remoteUser ->
                _uiState.value = _uiState.value.copy(
                    currentUser = remoteUser,
                    nameTextField = remoteUser.name
                )
                prefs.saveUser(remoteUser)
            }
        }
    }

    fun onTextNameChange(value: String) {
        _uiState.value = uiState.value.copy(
            nameTextField = value
        )
    }

    fun onNewPhoto(value: String) {
        _uiState.value = uiState.value.copy(
            newPhoto = if (uiState.value.newPhoto == value) "" else value
        )
    }

    fun onEditNameAlert() {
        _uiState.value = uiState.value.copy(
            showEditName = !uiState.value.showEditName
        )
    }

    fun onEditPhotoAlert() {
        _uiState.value = uiState.value.copy(
            showEditPhoto = !uiState.value.showEditPhoto
        )
    }

    fun onShowDeleteAlert() {
        _uiState.value = uiState.value.copy(
            showDeleteAlert = !uiState.value.showDeleteAlert
        )
    }

    fun checkSelected(url: String): Boolean {
        return (url == uiState.value.newPhoto)
    }

    fun onBackButton(action: () -> Unit) {
        if (uiState.value.backEnabled) {
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

    fun editUser(name: String, photo: String, active: Boolean) {
        val db = Firebase.firestore
        val docRef = db.collection("users").document(uiState.value.currentUser.id)
        var user = uiState.value.currentUser
        if (name != "") {
            docRef.update("name", name)
            user = user.copy(name = name)
        }
        if (photo != "") {
            docRef.update("photo", photo)
            user = user.copy(photo = photo)
        }
        if (!active) {
            docRef.update("active", false)
            user = user.copy(active = false)
        }
        prefs.saveUser(user)
    }

    fun logout(navController: NavHostController) {
        auth.signOut()
        prefs.clear()
        navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.HOME) { inclusive = true }
            popUpTo(Routes.EDIT) { inclusive = true }
        }
    }
}