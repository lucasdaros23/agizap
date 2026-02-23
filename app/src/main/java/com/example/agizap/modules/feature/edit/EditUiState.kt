package com.example.agizap.modules.feature.edit

import com.example.agizap.model.User

data class EditUiState(
    val currentUser: User = User(),
    val showEditName: Boolean = false,
    val showEditPhoto: Boolean = false,
    val showDeleteAlert: Boolean = false,
    val nameTextField: String = "",
    val backEnabled: Boolean = true,
    val newPhoto: String = ""
)
