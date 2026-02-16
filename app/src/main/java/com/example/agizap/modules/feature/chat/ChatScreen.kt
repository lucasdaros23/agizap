package com.example.agizap.modules.feature.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.agizap.model.Chat
import com.example.agizap.model.User
import com.example.agizap.modules.feature.chat.components.ChatTopBar

@Composable
fun ChatScreen(onClickBack: () -> Unit, onClickOther: () -> Unit, chat: Chat, user: User) {
    Box(){
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                ChatTopBar(
                    onClickBack = { onClickBack() },
                    onClickOther = { onClickOther() },
                    user = user,
                    chat = chat
                )
            }

        ) { innerPadding ->


        }
    }
}