package com.example.agizap.modules.feature.chatinfo

import com.example.agizap.modules.model.Chat
import com.example.agizap.modules.model.User

data class ChatInfoUiState(
    val users: List<User> = emptyList(),
    val chats: List<Chat> = emptyList(),
    val textField: String = "",
    val chat: Chat = Chat(),
    val user: User = User(),
    val backEnabled: Boolean = true,
    val currentUser: User = User(),
    val currentChatId: String = ""
)