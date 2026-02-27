package com.example.agizap.modules.feature.chat

import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.example.agizap.model.User

data class ChatUiState(
    val users: List<User> = emptyList(),
    val chat: Chat = Chat(),
    val messages: List<Message> = emptyList(),
    val showAlert: Boolean = false,
    val textField: String = "",
    val currentUser: User = User(),
    val chats: List<Chat> = emptyList(),
    val backEnabled: Boolean = true,
    val showPhoto: Boolean = false,
    val showDeleteAlert: Boolean = false,
)
