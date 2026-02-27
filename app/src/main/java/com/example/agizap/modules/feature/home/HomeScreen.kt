package com.example.agizap.modules.feature.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.agizap.R
import com.example.agizap.model.Message
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.IconButtonComponent
import com.example.agizap.modules.components.TextFieldComponent
import com.example.agizap.modules.components.UserProfilePicture
import com.example.agizap.modules.feature.home.components.AddChatButton
import com.example.agizap.modules.feature.home.components.AddChatDialog
import com.example.agizap.modules.feature.home.components.TopBarHome
import com.example.agizap.modules.feature.home.components.ChatCard
import com.example.agizap.modules.navigation.Routes

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val chats by viewModel.filterUsers().collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopBarHome(
                onClick1 = { viewModel.onShowAlert() },
                onClickLogout = { viewModel.logout(navController) },
                onClickEdit = { navController.navigate(Routes.EDIT) }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(innerPadding)) {
                TextFieldComponent(
                    value = uiState.textField,
                    onValueChange = { viewModel.onTextFieldChange(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    placeholder = "Pesquisar",
                    leadingIcon = {
                        IconButtonComponent(
                            painter = painterResource(R.drawable.search),
                            onClick = { },
                            size = 25
                        )
                    },
                )
                LazyColumn() {
                    items(chats) {
                        val name = viewModel.getChatName(it, uiState.currentUser)
                        val photo = viewModel.getChatPhoto(it, uiState.currentUser)
                        ChatCard(
                            chat = it,
                            onclick = { navController.navigate("chat/${it.id}") },
                            time = viewModel.formatTime(
                                viewModel.convertTime(
                                    it.messages.lastOrNull()?.time
                                        ?: System.currentTimeMillis()
                                )
                            ),
                            chatName = name,
                            checkSent = viewModel.checkSent(
                                uiState.currentUser,
                                it.messages.lastOrNull() ?: Message()
                            ),
                            photo = photo,
                            onPhotoClick = {
                                viewModel.setForPhoto(
                                    name = name,
                                    image = photo,
                                    chatId = it.id
                                )
                                viewModel.changeChatInfo(it.id)
                                viewModel.onShowPhoto()
                            },
                            isGroup = it.users.size > 2,
                            getUserName = { viewModel.getUserName(it) }
                        )
                    }
                }
            }

            AddChatButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(30.dp),
                onClick = {
                    viewModel.onShowAddChat()
                },
            )


            if (uiState.showAlert) {
                Alert(
                    title = "Essa função ainda não foi implementada",
                    confirmText = "OK",
                    confirmAction = { viewModel.onShowAlert() },
                    cancelAction = { viewModel.onShowAlert() }
                )
            }
            if (uiState.showAddChat) {
                val users = uiState.users.filter { it.id != uiState.currentUser.id && it.active }
                AddChatDialog(
                    users,
                    onItemClick = { selectedIds ->
                        var chatId =
                            if (selectedIds.size == 1) {
                                viewModel.checkChatExists(
                                    listOf(
                                        uiState.currentUser.id,
                                        selectedIds.firstOrNull()?:"")
                                )
                            } else {
                                val allUsers = (selectedIds + uiState.currentUser.id).toList()
                                viewModel.addChat(allUsers)
                            }

                        if (chatId == "") {
                            chatId = viewModel.addChat(
                                listOf(
                                    uiState.currentUser.id,
                                    selectedIds.find { true } ?: ""))
                        }
                        navController.navigate("chat/${chatId}")
                        viewModel.onShowAddChat()
                    },
                    cancelAction = { viewModel.onShowAddChat() },
                    checkSelected = { selectedIds, user ->
                        viewModel.checkSelected(selectedIds, user)
                    },
                    onCheckedClick = { checked, selectedIds, user ->
                        viewModel.onCheckedClick(checked, selectedIds, user)
                    }
                )
            }
            if (uiState.showPhoto) {
                UserProfilePicture(
                    name = uiState.nameForPhoto,
                    photo = uiState.imageForPhoto,
                    onQuit = { viewModel.onShowPhoto() },
                    onShowAlert = { viewModel.onShowAlert() },
                    onClickChat = {
                        navController.navigate("chat/${uiState.chatIdForPhoto}")
                        viewModel.onShowPhoto()
                    },
                    onHome = true,
                    onClickInfo = {  }
                )
            }
        }
    }
}