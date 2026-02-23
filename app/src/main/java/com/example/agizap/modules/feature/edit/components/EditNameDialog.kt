package com.example.agizap.modules.feature.edit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.agizap.modules.components.Alert
import com.example.agizap.modules.components.TextFieldComponent

@Composable
fun EditNameDialog(
    value: String,
    onValueChange: (String) -> Unit,
    confirmAction: () -> Unit,
    cancelAction: () -> Unit,
) {
    Alert(
        title = "Editar nome",
        confirmText = "Confirmar",
        confirmAction = { confirmAction() },
        cancelText = "Cancelar",
        cancelAction = { cancelAction() },
        content = {
            Column() {
                Spacer(Modifier.size(10.dp))
                OutlinedTextField(
                    value = value,
                    onValueChange = { onValueChange(it) },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        errorIndicatorColor = Color(0xffF53F27),
                        focusedContainerColor = Color(0x00000000),
                        unfocusedContainerColor = Color(0x00000000),
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        cursorColor = MaterialTheme.colorScheme.tertiary,
                    ),
                )
            }
        }
    )

}