package com.example.agizap.modules.feature.chatinfo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.ImageFromUrl
import com.example.agizap.modules.components.UserProfilePicture
import com.example.agizap.modules.feature.chatinfo.components.ChatInfoTopBar
import com.example.agizap.modules.feature.chatinfo.components.GroupUser
import com.example.agizap.modules.navigation.Routes

@Composable
fun ChatInfoScreen(
    viewModel: ChatInfoViewModel,
    chatId: String,
    navController: NavHostController,
) {
    LaunchedEffect(Unit) {
        viewModel.observeData()
        viewModel.getCurrentChat(chatId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val chat = uiState.chat
    val users = viewModel.getChatUsers(chatId)
    Scaffold(

        topBar = {
            ChatInfoTopBar(onReturn = {
                viewModel.onBackButton { navController.popBackStack() }
            })
        }
    ) { innerPadding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    ImageFromUrl(
                        viewModel.getChatPhoto(chatId),
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {}
                            .clip(CircleShape)
                    )
                    Spacer(Modifier.size(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center){
                        Text(viewModel.getChatName(chatId), style = MaterialTheme.typography.titleLarge)

                    }
                    Spacer(Modifier.size(10.dp))
                    Text(
                        if (viewModel.getEmail(chatId) != "") viewModel.getEmail(chatId)
                        else "${viewModel.getChatUsers(chatId).size} membros"
                    )
                    Column() {
                        users.forEach { user ->
                            GroupUser(
                                user = user,
                                isCurrentUser = user == uiState.currentUser,
                                onClick = {
                                    if (user.id == uiState.currentUser.id) navController.navigate(Routes.EDIT)
                                    else {
                                        viewModel.setUser(user)
                                        viewModel.onShowPhoto()
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.size(10.dp))
                        }
                    }
                }

            }
            if (uiState.showUserPhoto){
                UserProfilePicture(
                    name = uiState.photoUser.name,
                    photo = uiState.photoUser.photo,
                    onQuit = { viewModel.onShowPhoto() },
                    onShowAlert = { viewModel.onShowAlert() },
                    onClickChat = {
                        navController.navigate("chat/${viewModel.findUserChatId(uiState.photoUser.id)}")
                        viewModel.onShowPhoto()
                    },
                    onChat = false,
                    onInfo = true
                )
            }
            if(uiState.showAlert){
                Alert(

                )
            }
        }
    }
}