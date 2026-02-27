package com.example.agizap.modules.feature.chat.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.agizap.R
import com.example.agizap.model.Chat
import com.example.agizap.model.User
import com.example.agizap.modules.components.IconButtonComponent
import com.example.agizap.modules.components.ImageFromUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    onClickBack: () -> Unit,
    onClickOther: () -> Unit,
    chat: Chat, user: User,
    isGroup: Boolean,
    onClickPhoto: () -> Unit,
    anySelected: Boolean,
    onClickDelete: () -> Unit,
    selected: Int,
    usersString: String
) {
    TopAppBar(
        navigationIcon = {
            IconButtonComponent(
                painter = painterResource(R.drawable.voltar),
                onClick = {
                    onClickBack()
                },
                size = 30
            )
        },
        title = {
            if (anySelected) {
                Text(selected.toString())
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ImageFromUrl(
                        if (!isGroup) user.photo else chat.photo,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable { onClickPhoto() }
                    )
                    Spacer(modifier = Modifier.size(15.dp))
                    Column() {
                        Text(if (!isGroup) user.name else chat.name)
                        if (isGroup) {
                            Text(
                                usersString,
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }
        },
        actions = {
            if (anySelected) {
                IconButtonComponent(
                    painter = painterResource(R.drawable.delete),
                    onClick = { onClickDelete() },
                    size = 30
                )
            } else {
                IconButtonComponent(
                    painter = painterResource(R.drawable.ligar),
                    onClick = { onClickOther() },
                    size = 30
                )
                IconButtonComponent(
                    painter = painterResource(R.drawable.ligarvideo),
                    onClick = { onClickOther() },
                    size = 40
                )
                IconButtonComponent(
                    painter = painterResource(R.drawable.opcoes),
                    onClick = { onClickOther() },
                    size = 25
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.tertiary
        ),
    )
}