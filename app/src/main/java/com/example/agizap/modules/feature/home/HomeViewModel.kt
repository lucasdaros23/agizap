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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.agizap.modules.navigation.Routes
import com.example.agizap.modules.repositories.MessageRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.Instant
import javax.inject.Inject
import kotlin.collections.toList

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userRepo: UserRepository,
    private val chatRepo: ChatRepository,
    private val messageRepo: MessageRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()
    private val auth = FirebaseAuth.getInstance()

    init {
        onUpdate()
        observeData()
    }

    fun onUpdate() {
        viewModelScope.launch {
            val chatsList = chatRepo.getChats()
            val users = userRepo.getUsers()
            val currentUser = PreferencesManager(context).getUser()
            val currentUserChats = chatsList.filter { currentUser?.id in it.users }

            if (users.isNotEmpty()) _uiState.value = uiState.value.copy(users = users)
            if (currentUser != null) _uiState.value = uiState.value.copy(currentUser = currentUser)
            if (currentUserChats.isNotEmpty()) _uiState.value = uiState.value.copy(chats = currentUserChats)

        }
    }

    fun onTextFieldChange(value: String) {
        _uiState.value = uiState.value.copy(
            textField = value
        )
    }

    fun chatsSortedByDate() =
        uiState.value.chats.sortedByDescending { it.messages.lastOrNull()?.time ?: 0L }

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
                    time.hour,
                    time.minute
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

    fun checkChatExists(users: List<String>): String {
        val chat = uiState.value.chats.find { it.users.toSet() == users.toSet() }
        return  chat?.id ?: ""
    }

    fun getChatPhoto(chat: Chat, currentUser: User): String {
        return if (chat.users.size > 2) chat.photo
        else {
            uiState.value.users.find { it.id in chat.users && it.id != currentUser.id }?.photo
                ?: User().photo
        }
    }

    fun logout(navController: NavHostController) {
        auth.signOut()
        PreferencesManager(context).clear()
        navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.HOME) { inclusive = true }
        }
    }

    fun filterUsers(): List<Chat>{
        val list: List<Chat> = if (uiState.value.textField == "") chatsSortedByDate()
        else {
            chatsSortedByDate().filter{ getChatName(it, uiState.value.currentUser).contains(uiState.value.textField) }
        }

        return list.filter { uiState.value.currentUser.id in it.users }
    }

    fun observeUsers() {
        viewModelScope.launch {
            userRepo.listenUsers()
                .collect {
                    _uiState.value = uiState.value.copy(users = it)
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun observeChats() {
        viewModelScope.launch {
            chatRepo.listenChats()
                .flatMapLatest { chats ->
                    if (chats.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        val flows = chats.map { chat ->
                            messageRepo.listenMessages(chat.id)
                                .map { messages -> chat.copy(messages = messages) }
                        }
                        combine(flows) { it.toList() }
                    }
                }
                .collect { updatedChats ->
                    _uiState.value = _uiState.value.copy(chats = updatedChats)
                }
        }
    }

    fun onShowPhoto(){
        _uiState.value = uiState.value.copy(
            showPhoto = !uiState.value.showPhoto
        )
    }
    fun onShowEditProfile(){
        _uiState.value = uiState.value.copy(
            showEditProfile = !uiState.value.showEditProfile
        )
    }

    fun setForPhoto(name: String, image: String, chatId: String){
        _uiState.value = uiState.value.copy(
            nameForPhoto = name,
            imageForPhoto = image,
            chatIdForPhoto = chatId
        )
    }

    fun observeData() {
        _uiState.value = uiState.value.copy(showPhoto = false)
        onUpdate()
        observeUsers()
        observeChats()
    }

    fun onTextNameChange(value: String) {
        _uiState.value = uiState.value.copy(

        )
    }
}
