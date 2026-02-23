package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.agizap.model.User
import com.example.agizap.modules.components.Alert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChatDialog(
    users: List<User>,
    cancelAction: () -> Unit,
    onItemClick: (User) -> Unit) {
    Alert(
        title = "Escolha um usuário para conversar:",
        cancelText = "Cancelar",
        cancelAction = { cancelAction() },
        content = {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                if (users.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(users) {
                            UserCardSearch(it, onClick = { onItemClick(it) })
                            Spacer(Modifier.size(5.dp))
                        }
                    }
                } else {
                    Text("Nenhum usuário encontrado")
                }
            }
        }
    )

}