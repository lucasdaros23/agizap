package com.example.agizap.modules.feature.chat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.example.agizap.model.User
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.modules.repositories.ChatRepository
import com.example.agizap.modules.repositories.MessageRepository
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val messageRepo: MessageRepository,
    private val chatRepo: ChatRepository,
    private val userRepo: UserRepository,
) : ViewModel(){
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private var authListener: FirebaseAuth.AuthStateListener? = null

    fun onUpdate(chatId: String){
        viewModelScope.launch {
            val chatsList = chatRepo.getChats()
            val usersList = userRepo.getUsers()
            val currentUser = PreferencesManager(context).getUser() ?: User()
            val currentChat = chatsList.find { it.id == chatId } ?: Chat()
            val messages = currentChat.messages

            _uiState.value = uiState.value.copy(
                users = usersList,
                chat = currentChat,
                messages = messages,
                currentUser = currentUser
            )
        }
    }


    suspend fun getChatById(id: String) = chatRepo.getChats().find { it.id == id } ?: Chat()

    fun setChat(chatId: String){
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                chat = chatRepo.getChats().find { it.id == chatId } ?: Chat()
            )
        }
    }

    fun getChatMessages(chatId: String){
        viewModelScope.launch {
            _uiState.value = uiState.value.copy(
                messages = messageRepo.getMessages(chatId)
            )
        }
    }

    fun sendMessage(message: Message, chatId: String){
        messageRepo.addMessage(chatId, message)
    }

    fun onTextFieldChange(value: String){
        _uiState.value = uiState.value.copy(
            textField = value
        )
    }

    fun onShowAlert(){
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }

    fun checkSent(user: User, message: Message) = (user.id == message.userId)

    fun getChatName(chat: Chat) = if (chat.users.size == 2){
        chat.users.find { it == uiState.value.currentUser.id } ?: ""
    } else chat.name

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTime(time: Long) = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalDateTime, card: Boolean): String {
        val now = LocalDateTime.now()
        val today = now.dayOfYear
        when {
            today == time.dayOfYear &&
                    now.year == time.year -> return if (card) let{ String.format("%02d:%02d", now.hour, now.minute) } else "Hoje"
            now.year == time.year &&
                    today == time.dayOfYear -1 -> return "Ontem"
            now.year == time.year &&
                    time.dayOfYear in (today -2 .. today - 7) -> return time.dayOfWeek.toString()
            else -> return let{ String.format("%02d/%02d/%04d", time.dayOfMonth, time.monthValue, time.year) }
        }
    }
    fun findUserById(id: String) = uiState.value.users.find { it.id == id } ?: User()

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDateComponent(message: Message): Boolean{
        val list = uiState.value.messages
        val index = list.indexOf(message)
        if (index == 0) return true
        val time = convertTime(message.time)
        val time2 = convertTime(list[index-1].time)
        return !(time.dayOfYear == time2.dayOfYear && time.year == time2.year)
    }

    override fun onCleared() {
        super.onCleared()
        authListener?.let { auth.removeAuthStateListener(it) }
        authListener = null
    }

}