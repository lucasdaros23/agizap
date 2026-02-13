package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agizap.model.User
import com.example.agizap.modules.components.AlertTextButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChatDialog(users: List<User>, onClick: () -> Unit, onItemClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .fillMaxWidth()
            ) {
                Text("Escolha um usuário para conversar:")
                Spacer(Modifier.size(10.dp))
                if (users.isNotEmpty()) {

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(users) {
                            UserCardSearch(it, onClick = { onItemClick()})
                            Spacer(Modifier.size(5.dp))
                        }
                    }
                } else {
                    Text("Nenhum usuário encontrado")
                }
                Row() {
                    AlertTextButton(
                        text = "Cancelar",
                        onClick = { onClick() }
                    )
                }
            }
        }
    }
}