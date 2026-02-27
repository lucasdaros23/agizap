package com.example.agizap.modules.feature.chat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.example.agizap.model.User
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.modules.repositories.ChatRepository
import com.example.agizap.modules.repositories.MessageRepository
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepo: MessageRepository,
    private val chatRepo: ChatRepository,
    private val userRepo: UserRepository,
    private val prefs: PreferencesManager
) : ViewModel(){
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    fun onUpdate(chatId: String) {
        viewModelScope.launch {
            val users = userRepo.getUsers()
            val chats = chatRepo.getChats()
            val currentUser = prefs.getUser()
            val currentChat = chats.find { it.id == chatId }

            if (users.isNotEmpty()) _uiState.value = uiState.value.copy(users = users)
            if (currentUser != null) _uiState.value = uiState.value.copy(currentUser = currentUser)
            if (currentChat != null) _uiState.value = uiState.value.copy(chat = currentChat)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTime(time: Long) = LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault())

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(time: LocalDateTime, card: Boolean): String {
        val now = LocalDateTime.now()
        val today = now.dayOfYear
        when {
            today == time.dayOfYear &&
                    now.year == time.year -> return if (card) let{ String.format("%02d:%02d", time.hour, time.minute) } else "Hoje"
            now.year == time.year &&
                    today == time.dayOfYear -1 -> return "Ontem"
            now.year == time.year &&
                    time.dayOfYear in (today -2 .. today - 7) -> return time.dayOfWeek.toString()
            else -> return let{ String.format("%02d/%02d/%04d", time.dayOfMonth, time.monthValue, time.year) }
        }
    }

    @SuppressLint("DefaultLocale")
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMessageTime(time: LocalDateTime) = let{ String.format("%02d:%02d", time.hour, time.minute) }


    fun observeMessages(chatId: String) {
        viewModelScope.launch {
            messageRepo.listenMessages(chatId)
                .collect {
                    _uiState.value = uiState.value.copy(messages = it)
                }
        }
    }

    fun observeUsers() {
        viewModelScope.launch {
            userRepo.listenUsers().collect {
                _uiState.value = _uiState.value.copy(users = it)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun checkDateComponent(message: Message): Boolean{
        val list = uiState.value.messages
        val index = list.indexOf(message)
        if (index == 0) return true
        val time = convertTime(message.time)
        val time2 = convertTime(list[index-1].time)
        return !(time.dayOfYear == time2.dayOfYear && time.year == time2.year)
    }

    fun observeData(chatId: String){
        observeUsers()
        observeMessages(chatId)
        onUpdate(chatId)
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

    fun onShowPhoto(){
        _uiState.value = uiState.value.copy(
            showPhoto = !uiState.value.showPhoto
        )
    }

    fun onShowDelete(){
        _uiState.value = uiState.value.copy(
            showDeleteAlert = !uiState.value.showDeleteAlert
        )
    }

    fun onMessageDelete(list: Set<String>, chatId: String){
        val db = Firebase.firestore
        val chatRef = db.collection("chats").document(chatId)
        list.forEach {
            chatRef.collection("messages").document(it).update("deleted", true)
        }
    }

    fun chatMembersString(): String{
        val users = uiState.value.users.filter { it.id in uiState.value.chat.users }
        var usersString = ""
        users.forEach { user -> usersString = usersString + user.name + if (users.indexOf(user) < users.size-1) ", " else "" }
        return usersString
    }

    fun getUserName(id: String) = uiState.value.users.find { it.id == id }?.name ?: ""
}