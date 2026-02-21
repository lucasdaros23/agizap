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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.agizap.modules.feature.home.components.AddChatButton
import com.example.agizap.modules.feature.home.components.AddChatDialog
import com.example.agizap.modules.feature.home.components.TopBarHome
import com.example.agizap.modules.feature.home.components.ChatCard

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopBarHome(
                onClick1 = { viewModel.onShowAlert() },
                onClickLogout = { viewModel.logout(navController) }
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
                    items(viewModel.filterUsers()) {
                        ChatCard(
                            chat = it,
                            onclick = { navController.navigate("chat/${it.id}") },
                            time = viewModel.formatTime(
                                viewModel.convertTime(
                                    it.messages.lastOrNull()?.time
                                        ?: System.currentTimeMillis()
                                )
                            ),
                            chatName = viewModel.getChatName(it, uiState.currentUser),
                            checkSent = viewModel.checkSent(
                                uiState.currentUser,
                                it.messages.lastOrNull() ?: Message()
                            ),
                            photo = viewModel.getChatPhoto(it, uiState.currentUser)
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
                }
            )


            if (uiState.showAlert) {
                Alert(
                    title = "Essa função ainda não foi implementada",
                    "",
                    "OK",
                    "",
                    { viewModel.onShowAlert() },
                )
            }
            if (uiState.showAddChat) {
                AddChatDialog(
                    uiState.users.filter { it.id != uiState.currentUser.id },
                    onClick = { viewModel.onShowAddChat() },
                    onItemClick = {
                        var chatId = viewModel.checkChatExists(listOf(uiState.currentUser.id, it.id))
                        if (chatId == "") {
                            chatId = viewModel.addChat(listOf(uiState.currentUser.id, it.id))
                        }
                        navController.navigate("chat/${chatId}")
                        viewModel.onShowAddChat()
                    }
                )
            }
        }
    }
}