package com.example.agizap.modules.feature.chat

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agizap.model.Message
import com.example.agizap.model.User
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.feature.chat.components.Background
import com.example.agizap.modules.feature.chat.components.ChatBottomBar
import com.example.agizap.modules.feature.chat.components.ChatTopBar
import com.example.agizap.modules.feature.chat.components.DateComponent
import com.example.agizap.modules.feature.chat.components.MessageComponent

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    chatId: String,
    navController: NavHostController
) {
    LaunchedEffect(chatId) {
        viewModel.onUpdate(chatId)
        viewModel.observeMessages(chatId)
    }
    val uiState by viewModel.uiState.collectAsState()
    Box() {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                val otherUser =
                    uiState.users.find { it.id in uiState.chat.users && it.id != uiState.currentUser.id }
                        ?: User()

                ChatTopBar(
                    onClickBack = {
                        navController.popBackStack()
                    },
                    onClickOther = { viewModel.onShowAlert() },
                    user = otherUser,
                    chat = uiState.chat,
                    isGroup = (uiState.users.size > 2)
                )
            },
            bottomBar = {
                ChatBottomBar(
                    value = uiState.textField,
                    onValueChange = { viewModel.onTextFieldChange(it) },
                    onShowAlertClick = { viewModel.onShowAlert() },
                    sendMessage = {
                        viewModel.sendMessage(
                            Message(
                                text = uiState.textField.trim(),
                                userId = uiState.currentUser.id,
                                time = System.currentTimeMillis(),
                            ),
                            chatId = uiState.chat.id
                        )
                        viewModel.onTextFieldChange("")

                    }

                )
            }


        ) { innerPadding ->
            val listState = rememberLazyListState()
            val messages = uiState.messages

            LaunchedEffect(messages.size) {
                if (messages.isNotEmpty()) {
                    listState.scrollToItem(messages.lastIndex)
                }
            }
            Background()
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                state = listState
            ) {
                items(messages) {
                    if (viewModel.checkDateComponent(it)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            DateComponent(
                                text = viewModel.formatTime(
                                    time = viewModel.convertTime(it.time),
                                    card = false
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (viewModel.checkSent(uiState.currentUser, it))
                            Arrangement.End else Arrangement.Start
                    ) {
                        MessageComponent(
                            message = it,
                            formatedTime = viewModel.getMessageTime(
                                time = viewModel.convertTime(it.time),
                            ),
                            sent = viewModel.checkSent(
                                user = uiState.currentUser,
                                message = it
                            )
                        )
                    }

                }
            }
        }
        if (uiState.showAlert) {
            Alert(
                title = "Essa função ainda não foi implementada",
                "",
                "OK",
                "",
                { viewModel.onShowAlert() },
            )
        }
    }
}