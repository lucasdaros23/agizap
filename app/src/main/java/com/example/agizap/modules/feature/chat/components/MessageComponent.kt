package com.example.agizap.modules.feature.chat.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agizap.model.Message
import com.example.agizap.modules.components.SeenIcon
import org.w3c.dom.Text

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageComponent(
    message: Message,
    formatedTime: String,
    sent: Boolean,
    selected: Boolean,
    onChangeSelect: () -> Unit,
    anySelected: Boolean,
    isGroup: Boolean,
    userName: String,
    isRepeatedMessage: Boolean
) {
    Box(
        Modifier
            .fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .padding(horizontal = 22.dp, vertical = 2.dp)
                .background(
                    color = if (sent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(11.dp)
                )
                .clickable {}
                .align(alignment = if (sent) Alignment.CenterEnd else Alignment.CenterStart)
        ) {
            if (message.text.length < 17 || message.deleted) {
                Column(){
                    if (!sent && isGroup && !isRepeatedMessage){
                        Text(
                            userName,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(
                                top = 5.dp,
                                start = 10.dp,
                                end = 10.dp
                            )
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = if (!message.deleted) message.text else "\uD83D\uDEAB Mensagem apagada",
                            color = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.padding(
                                end = 10.dp,
                                top = 5.dp,
                                start = 10.dp,
                                bottom = 7.dp
                            ),
                            fontSize = 20.sp
                        )
                        Row(
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .weight(1f, fill = false),
                            verticalAlignment = Alignment.Bottom
                        ) {

                            Text(
                                text = (formatedTime),
                                color = MaterialTheme.colorScheme.tertiary,
                                fontSize = 13.sp,
                            )
                            if (sent && !message.deleted) {
                                SeenIcon(
                                    modifier = Modifier
                                        .size(22.dp)
                                        .padding(bottom = 2.dp)
                                )
                            }
                        }
                    }
                }            } else {
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = message.text,
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.padding(
                            end = 10.dp,
                            top = 10.dp,
                            start = 10.dp,
                            bottom = 0.dp
                        ),
                        fontSize = 20.sp
                    )
                    Row(
                        modifier = Modifier
                            .padding(end = 4.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {

                        Text(
                            text = (formatedTime),
                            color = MaterialTheme.colorScheme.tertiary,
                            fontSize = 13.sp,
                        )
                        if (sent) {
                            SeenIcon(
                                modifier = Modifier
                                    .size(22.dp)
                                    .padding(bottom = 2.dp)
                            )
                        }
                    }
                }
            }

        }
        Box(
            Modifier
                .matchParentSize()
                .background(if (selected) MaterialTheme.colorScheme.primary.copy(alpha = .5f) else Color.Transparent)
                .combinedClickable(
                    onClick = { if (!message.deleted && sent && (selected || anySelected)) onChangeSelect() },
                    onLongClick = { if (!message.deleted && sent) onChangeSelect() }
                )
            ){
        }
    }
}

@Preview
@Composable
private fun SentMessagePreview() {
    MessageComponent(
        Message(text = "oiiiii"),
        formatedTime = "15:26",
        sent = true,
        selected = false,
        onChangeSelect = {},
        anySelected = false,
        isGroup = false,
        userName = "",
        isRepeatedMessage = false
    )
}
@Preview
@Composable
private fun RecievedMessagePreview() {
    MessageComponent(
        Message(text = "oie"),
        formatedTime = "15:28",
        sent = false,
        selected = true,
        onChangeSelect = {},
        anySelected = false,
        isGroup = true,
        userName = "sla",
        isRepeatedMessage = false
    )
}