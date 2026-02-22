package com.example.agizap.modules.feature.home

import com.example.agizap.model.Chat
import com.example.agizap.model.User

data class HomeUiState(
    val textField: String = "",
    val users: List<User> = emptyList(),
    val chats: List<Chat> = emptyList(),
    val showAlert: Boolean = false,
    val currentUser: User = User(),
    val showAddChat: Boolean = false,
    val showPhoto: Boolean = false,
    val nameForPhoto: String = "",
    val imageForPhoto: String = "",
    val chatIdForPhoto: String = "",
)
