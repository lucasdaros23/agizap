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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.agizap.model.User
import com.example.agizap.modules.components.Alert

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChatDialog(
    users: List<User>,
    cancelAction: () -> Unit,
    onItemClick: (Set<String>) -> Unit,
    checkSelected: (Set<String>, User) -> Boolean,
    onCheckedClick: (Boolean, Set<String>, User) -> Set<String>
) {
    var selectedIds by remember { mutableStateOf(setOf<String>()) }
    val anySelected = selectedIds.isNotEmpty()
    Alert(
        title = "Escolha um usuário para conversar:",
        cancelAction = { cancelAction() },
        content = {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                if (users.isNotEmpty()) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(users) { user ->
                            Row(){
                                val checked = checkSelected(selectedIds, user)
                                UserCardSearch(user, onClick = { selectedIds = onCheckedClick(checked, selectedIds, user) })
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = { selectedIds = onCheckedClick(checked, selectedIds, user) }
                                )
                            }
                            Spacer(Modifier.size(5.dp))
                        }
                    }
                } else {
                    Text("Nenhum usuário encontrado")
                }
                Button(
                    onClick = {
                        if (anySelected) onItemClick(selectedIds)
                    },
                    shape = RoundedCornerShape(15.dp),
                    colors = if (anySelected) ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.tertiary
                    )
                    else ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    )
                    ,
                    modifier = Modifier.align(Alignment.End)
                ){
                    Text(if (selectedIds.size == 1) "Enviar mensagem" else "Criar grupo",
                        style = MaterialTheme.typography.titleSmall,
                        )
                }
            }
        }
    )

}