package com.example.agizap.modules.feature.chatinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agizap.modules.model.Chat
import com.example.agizap.modules.model.User
import com.example.agizap.modules.preferences.PreferencesManager
import com.example.agizap.modules.repositories.ChatRepository
import com.example.agizap.modules.repositories.UserRepository
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.forEach

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

    fun getChatUsers(chatId: String) : List<User> {
        val usersString = (uiState.value.chats.find { it.id == chatId } ?: Chat()).users
        val currentUser = uiState.value.currentUser
        val list = mutableListOf(currentUser)
        uiState.value.users.forEach {
            if (usersString.contains(it.id) && it.id != currentUser.id) list.add(it)
        }
        return list
    }

    fun onShowPhoto(){
        _uiState.value = uiState.value.copy(
            showUserPhoto = !uiState.value.showUserPhoto
        )
    }

    fun onShowAlert() {
        _uiState.value = uiState.value.copy(showAlert = !uiState.value.showAlert)
    }

    fun onShowEditNameAlert() {
        _uiState.value = uiState.value.copy(showEditNameAlert = !uiState.value.showEditNameAlert)
    }

    fun setUser(user: User){
        _uiState.value = uiState.value.copy(
            photoUser = user
        )
    }

    fun setAlertText(text: String){
        _uiState.value = uiState.value.copy(
            alertText = text
        )
    }

    fun findUserChatId(userId: String): String{
        val list = listOf(userId, uiState.value.currentUser.id)
        val chat = uiState.value.chats.find {
            it.users.size == 2 &&
                    it.users.containsAll(list) }
        return chat?.id ?: addChat(list)
    }
    fun addChat(users: List<String>): String {
        val chat = Chat(users = users)
        val chatId = chatRepo.addChat(chat)
        return chatId
    }

    fun editName(name: String, chatId: String){
        val db = Firebase.firestore
        val docRef = db.collection("chats").document(chatId)
        if (name.trim() != ""){
            docRef.update("name", name)
        }
    }

    fun editUser(name: String, photo: String, active: Boolean) {
        val db = Firebase.firestore
        val docRef = db.collection("users").document(uiState.value.currentUser.id)
        var user = uiState.value.currentUser
        if (name.trim() != "") {
            docRef.update("name", name)
            user = user.copy(name = name)
        }
        if (photo != "") {
            docRef.update("photo", photo)
            user = user.copy(photo = photo)
        }
        if (!active) {
            docRef.update("active", false)
            user = user.copy(active = false)
        }
        prefs.saveUser(user)
    }
}