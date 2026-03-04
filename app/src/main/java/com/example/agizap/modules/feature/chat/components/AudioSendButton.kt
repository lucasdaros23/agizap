package com.example.agizap.modules.feature.chat.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.agizap.R
import com.example.agizap.modules.components.IconButtonComponent

@Composable
fun AudioSendButton(
    text: String,
    onTextFieldChange: (String) -> Unit,
    sendMessage: () -> Unit,
    onShowAlertClick: () -> Unit,
) {
    IconButtonComponent(
        painter = painterResource(if (text == "") R.drawable.mic else R.drawable.aviao),
        size = 25,
        onClick = {
            if (text != "") {
                sendMessage()
                onTextFieldChange("")
            } else {
                onShowAlertClick()
            }
        },
    )
}