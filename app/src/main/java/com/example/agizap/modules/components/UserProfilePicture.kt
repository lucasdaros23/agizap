package com.example.agizap.modules.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agizap.R
import com.example.agizap.model.Chat
import com.example.agizap.model.User

@Composable
fun UserProfilePicture(
    name: String,
    photo: String,
    onQuit: () -> Unit,
    onClickChat: () -> Unit,
    onShowAlert: () -> Unit,
    onHome: Boolean
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0x66000000))
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { onQuit() }
        ){

        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.fillMaxWidth(.7f)
            ) {
                ImageFromUrl(
                    url = photo,
                    modifier = Modifier
                        .size(280.dp)
                        .clip(shape = RectangleShape)
                )
                Box(
                    Modifier
                        .background(Color(0x66000000))
                        .fillMaxWidth()
                        .padding(6.dp)
                ) {
                    Text(
                        name,
                        fontSize = 20.sp
                    )
                }
            }
            Row(
                Modifier.fillMaxWidth(.7f).background(MaterialTheme.colorScheme.background),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                if (onHome){
                    IconButtonComponent(
                        painter = painterResource(R.drawable.message),
                        onClick = { onClickChat() },
                        size = 20
                    )
                }
                IconButtonComponent(
                    painter = painterResource(R.drawable.ligar),
                    onClick = { onShowAlert() },
                    size = 20
                )
                IconButtonComponent(
                    painter = painterResource(R.drawable.ligarvideo),
                    onClick = { onShowAlert() },
                    size = 20
                )
            }
        }
    }
}