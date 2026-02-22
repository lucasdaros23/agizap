package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agizap.R
import com.example.agizap.model.User
import com.example.agizap.modules.components.ImageFromUrl
import com.example.agizap.modules.feature.register.components.ReturnButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    user: User,
    onDismiss: () -> Unit,
    onChangeName: () -> Unit,
    onChangePhoto: () -> Unit,
    onDeleteAccount: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically){
                        ReturnButton(
                            onClick = { onDismiss() }
                        )
                        Text("Perfil", fontSize = 28.sp)
                    }
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ImageFromUrl(
                            user.photo,
                            modifier = Modifier
                                .size(200.dp)
                                .clickable{}
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.size(10.dp))
                        TextButton(
                            onClick = { onChangePhoto() }
                        ) {
                            Text("Editar", color = MaterialTheme.colorScheme.primary, fontSize = 15.sp)
                        }
                    }
                    Spacer(Modifier.size(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable{ onChangeName() }
                            .padding(vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically,

                    ){
                        Spacer(Modifier.size(30.dp))
                        Icon(
                            painter = painterResource(R.drawable.profile),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(Modifier.size(20.dp))
                        Column() {
                            Text("Nome", fontSize = 20.sp, color = MaterialTheme.colorScheme.tertiary)
                            Text(user.name, fontSize = 18.sp, color = MaterialTheme.colorScheme.onTertiary)
                        }
                    }
                    Spacer(Modifier.size(10.dp))
                    TextButton(
                        onClick = { onDeleteAccount() },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text("Desativar conta", color = MaterialTheme.colorScheme.primary, fontSize = 15.sp)
                    }


                }

            }
        }

}