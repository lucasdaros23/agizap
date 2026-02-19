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
import com.example.agizap.modules.preferences.PreferencesManager
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepo: UserRepository,
    private val chatRepo: ChatRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init{
        onUpdate()
    }

    fun onUpdate() {
        viewModelScope.launch {
            val chatsList = chatRepo.getChats()
            val usersList = userRepo.getUsers()
            val currentUser = PreferencesManager(context).getUser() ?: User()
            val currentUserChats = chatsList.filter { currentUser.id in it.users }

            _uiState.value = uiState.value.copy(
                users = usersList,
                currentUser = currentUser,
                chats = currentUserChats
            )
        }
    }

    fun onTextFieldChange(value: String) {
        _uiState.value = uiState.value.copy(
            textField = value
        )
    }

    fun chatsSortedByDate() =
        uiState.value.chats.sortedBy { it.messages.lastOrNull()?.time ?: 0L }

    fun onShowAlert() {
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }

    fun checkSent(user: User, message: Message) = (user.id == message.userId)

    fun getChatName(chat: Chat, currentUser: User): String {
        return if (chat.users.size > 2) chat.name
        else {
            uiState.value.users.find { it.id in chat.users && it.id != currentUser.id }?.name
                ?: User().name
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTime(time: Long) =
        LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalDateTime): String {
        val now = LocalDateTime.now()
        val today = now.dayOfYear
        when {
            today == time.dayOfYear &&
                    now.year == time.year -> return let {
                String.format(
                    "%02d:%02d",
                    now.hour,
                    now.minute
                )
            }

            now.year == time.year &&
                    today == time.dayOfYear - 1 -> return "Ontem"

            now.year == time.year &&
                    time.dayOfYear in (today - 2..today - 7) -> return time.dayOfWeek.toString()

            else -> return let {
                String.format(
                    "%02d/%02d/%04d",
                    time.dayOfMonth,
                    time.monthValue,
                    time.year
                )
            }
        }
    }

    fun findUserById(id: String) = uiState.value.users.find { it.id == id } ?: User()


    fun onShowAddChat() {
        viewModelScope.launch {
            val usersList = userRepo.getUsers()
            _uiState.value = uiState.value.copy(
                users = usersList,
                showAddChat = !uiState.value.showAddChat
            )
        }
    }

    fun addChat(users: List<String>): String {
        val chat = Chat(users = users)
        val chatId = chatRepo.addChat(chat)
        return chatId
    }

    fun checkChatExists(users: List<User>): Boolean {
        val chat = uiState.value.chats.find { it.users.toSet() == users.toSet() }
        return (chat != null)
    }

    fun getChatPhoto(chat: Chat, currentUser: User): String {
        return if (chat.users.size > 2) chat.photo
        else {
            uiState.value.users.find { it.id in chat.users && it.id != currentUser.id }?.photo
                ?: User().photo
        }
    }

}
