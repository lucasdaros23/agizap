package com.example.agizap.modules.feature.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agizap.model.Message
import com.example.agizap.modules.components.SeenIcon

@Composable
fun MessageComponent(message: Message, formatedTime: String, sent: Boolean) {
    Box(
        modifier = Modifier
            .padding(2.dp)
            .background(
                color = if (sent) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(11.dp)
            )
    ){
        if(message.text.length < 17) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = message.text,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(
                        end = 10.dp,
                        top = 10.dp,
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
                    if (sent) {
                        SeenIcon(
                            modifier = Modifier
                                .size(22.dp)
                                .padding(bottom = 2.dp)
                        )
                    }
                }
            }
        }else{
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ){
                Text(
                    text = message.text,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(end = 10.dp, top = 10.dp, start = 10.dp, bottom = 0.dp),
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
                    if (sent){
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
}