package com.example.agizap.modules.feature.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.modules.repositories.ChatRepository
import com.example.agizap.modules.repositories.UserRepository
import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.example.agizap.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.Instant

class HomeViewModel (
    private val userRepo: UserRepository = UserRepository(),
    private val chatRepo: ChatRepository = ChatRepository(),

    ): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUsers(){
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                users = userRepo.getUsers(),
            )
        }
    }

    fun updateChats(){
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                chats = chatRepo.getChats(),
            )
        }
    }

    fun onLaunch(){
        updateUsers()
        updateChats()
    }

    fun userAdd(user: User) {
        userRepo.addUser(user)
        updateUsers()
    }

    fun chatAdd(chat: Chat){
        chatRepo.addChat(chat)
        updateChats()
    }

    fun onTextFieldChange(novoTexto: String){
        _uiState.value = uiState.value.copy(
            textField = novoTexto
        )
    }

    fun chatsSortedByDate() = uiState.value.chats.sortedBy { it.messages.last().time }
    fun onShowAlertHome(){
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }

    fun checkSent(user: User, message: Message) = (user.id == message.userId)

    fun getChatName(chat: Chat) = if (chat.users.size == 2) "" else chat.name // MUDAR AQUI PRA QUANDO POR O AUTH E BOTAR O FIND WHERE DIFERENTE DE GETCURRENTUSER
    
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTime(time: Long) = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalDateTime): String {
        val now = LocalDateTime.now()
        val today = now.dayOfYear
        when {
            today == time.dayOfYear &&
                    now.year == time.year -> return let{ String.format("%02d:%02d", now.hour, now.minute) }
            now.year == time.year &&
                    today == time.dayOfYear -1 -> return "Ontem"
            now.year == time.year &&
                    time.dayOfYear in (today -2 .. today - 7) -> return time.dayOfWeek.toString()
            else -> return let{ String.format("%02d/%02d/%04d", time.dayOfMonth, time.monthValue, time.year) }
        }
    }
}