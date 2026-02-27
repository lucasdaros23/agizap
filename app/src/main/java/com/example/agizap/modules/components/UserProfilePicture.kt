package com.example.agizap.modules.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.DialogProperties
import com.example.agizap.R
import com.example.agizap.model.Chat
import com.example.agizap.model.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfilePicture(
    name: String,
    photo: String,
    onQuit: () -> Unit,
    onClickChat: () -> Unit,
    onShowAlert: () -> Unit,
    onHome: Boolean,
    onClickInfo: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onQuit() },
        properties = DialogProperties(
            usePlatformDefaultWidth = true
        )
    ) {
        Surface(
            modifier = Modifier.width(300.dp).height(330.dp)
        ){
                Box(
                ) {
                    ImageFromUrl(
                        url = photo,
                        modifier = Modifier.fillMaxSize()
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
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        if (onHome) {
                            IconButtonComponent(
                                painter = painterResource(R.drawable.message),
                                onClick = { onClickChat() },
                                size = 30,
                            )
                        }
                        IconButtonComponent(
                            painter = painterResource(R.drawable.ligar),
                            onClick = { onShowAlert() },
                            size = 30
                        )
                        IconButtonComponent(
                            painter = painterResource(R.drawable.ligarvideo),
                            onClick = { onShowAlert() },
                            size = 30
                        )
                        IconButtonComponent(
                            painter = painterResource(R.drawable.info),
                            onClick = { onClickInfo() },
                            size = 25
                        )
                    }
                }
                Box {

                }
            }
        }
    }
