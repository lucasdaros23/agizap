package com.example.agizap.modules.feature.chatinfo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.agizap.modules.components.ImageFromUrl
import com.example.agizap.modules.feature.chatinfo.components.ChatInfoTopBar

@Composable
fun ChatInfoScreen(
    viewModel: ChatInfoViewModel,
    chatId: String,
    navController: NavHostController,
) {
    LaunchedEffect(Unit) {
        viewModel.observeData()
    }
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
                .padding(innerPadding),
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
                    Text(viewModel.getChatName(chatId))
                    Spacer(Modifier.size(20.dp))
                    if (viewModel.getEmail(chatId) != "") Text(viewModel.getEmail(chatId))
                }

            }
        }
    }
}