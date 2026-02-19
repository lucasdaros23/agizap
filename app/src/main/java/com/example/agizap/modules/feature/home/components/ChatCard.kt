package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agizap.R
import com.example.agizap.model.Chat
import com.example.agizap.modules.components.ImageComponent
import com.example.agizap.modules.components.SeenIcon

@Composable
fun ChatCard(chat: Chat, onclick: () -> Unit, time: String, chatName: String, checkSent: Boolean) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onclick() })
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(15.dp)
    ){
        val mensagens = chat.messages
        val lastMessage = mensagens.lastOrNull()
        ImageComponent(R.drawable.foto)
        Spacer(modifier = Modifier.size(10.dp))
        Column() {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = chatName, fontSize = 20.sp)
                Text(
                    text = time,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
            Row(){
                if (checkSent){
                    SeenIcon(modifier = Modifier.size(24.dp))
                }
                Text(text = lastMessage?.text ?: "", color = MaterialTheme.colorScheme.onTertiary)
            }
        }
    }
}