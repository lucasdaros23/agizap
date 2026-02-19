package com.example.agizap.modules.feature.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.agizap.R
import com.example.agizap.model.Message
import com.example.agizap.modules.components.IconButtonComponent
import com.example.agizap.modules.components.TextFieldComponent

@Composable
fun ChatBottomBar(
    value: String,
    onValueChange: (String) -> Unit,
    onShowAlertClick: () -> Unit,
    sendMessage: () -> Unit,
) {
    BottomAppBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.ime)
            .imePadding(),
        containerColor = Color.Transparent,
    ) {
        Row() {
            TextFieldComponent(
                value = value,
                onValueChange = onValueChange,
                placeholder = "Mensagem",
                leadingIcon = {
                    IconButtonComponent(
                        painter = painterResource(R.drawable.figurinha),
                        size = 30,
                        onClick = { onShowAlertClick() }
                    )
                },
                trailingIcon = {
                    Row() {
                        if (value == "") {
                            IconButtonComponent(
                                painter = painterResource(R.drawable.camera),
                                size = 30,
                                onClick = { onShowAlertClick() }
                            )
                        }
                        IconButtonComponent(
                            painter = painterResource(R.drawable.anexo),
                            size = 30,
                            onClick = { onShowAlertClick() }
                        )
                    }
                },
                modifier = Modifier
                    .weight(7f)
                    .padding(end = 10.dp)
            )
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )

            ) {
                AudioSendButton(
                    text = value,
                    onTextFieldChange = { onValueChange(value) },
                    sendMessage = { sendMessage() },
                    onShowAlertClick = { onShowAlertClick() },
                )
            }
        }
    }
}