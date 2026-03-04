package com.example.agizap.modules.feature.chatinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.modules.model.Chat
import com.example.agizap.modules.model.User
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.modules.repositories.ChatRepository
import com.example.agizap.modules.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatInfoViewModel@Inject constructor(
    private val chatRepo: ChatRepository,
    private val userRepo: UserRepository,
    private val prefs: PreferencesManager,
    ) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatInfoUiState())
    val uiState = _uiState.asStateFlow()

    fun observeUsers() {
        viewModelScope.launch {
            userRepo.listenUsers()
                .collect {
                    _uiState.value = uiState.value.copy(users = it)
                }
        }
    }

    fun observeChats() {
        viewModelScope.launch {
            chatRepo.listenChats()
                .collect {
                    _uiState.value = uiState.value.copy(chats = it)
                }
        }
    }

    fun onTextNameChange(value: String) {
        _uiState.value = uiState.value.copy(
            textField = value
        )
    }

    fun observeData(){
        getCurrentUser()
        observeUsers()
        observeChats()
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

    fun getCurrentUser() {
        val user = prefs.getUser() ?: User()
        _uiState.value = uiState.value.copy(
            currentUser = user,
            textField = user.name,
        )
    }

    fun getCurrentChat(chatId: String) = uiState.value.chats.find { it.id == chatId } ?: Chat()

    fun getChatName(chatId: String): String{
        val chat = uiState.value.chats.find { it.id == chatId } ?: Chat()
        if (chat.users.size > 2) return chat.name
        else {
            val otherUserId = chat.users.find { it != uiState.value.currentUser.id }
            val user = uiState.value.users.find { it.id == otherUserId }
            return user?.name ?: ""
        }
    }
    fun getChatPhoto(chatId: String): String{
        val chat = uiState.value.chats.find { it.id == chatId } ?: Chat()
        if (chat.users.size > 2) return chat.photo
        else {
            val otherUserId = chat.users.find { it != uiState.value.currentUser.id }
            val user = uiState.value.users.find { it.id == otherUserId }
            return user?.photo ?: ""
        }
    }
    fun getEmail(chatId: String): String{
        val chat = uiState.value.chats.find { it.id == chatId } ?: Chat()
        if (chat.users.size == 2){
            val otherUserId = chat.users.find { it != uiState.value.currentUser.id }
            val user = uiState.value.users.find { it.id == otherUserId }
            return user?.email ?: ""
        }
        else return ""
    }
}