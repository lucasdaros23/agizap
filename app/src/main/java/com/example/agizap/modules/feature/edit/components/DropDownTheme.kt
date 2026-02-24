package com.example.agizap.modules.feature.edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.agizap.R
import com.example.agizap.modules.components.IconButtonComponent
import com.example.agizap.modules.preferences.PreferencesManager

@Composable
fun DropDownTheme(
    setPreference: (String) -> Unit,
    getPreference: () -> String
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Spacer(Modifier.size(30.dp))
        Icon(
            painter = painterResource(R.drawable.theme),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        Spacer(Modifier.size(20.dp))
        Box {
            Column(
                Modifier.clickable { expanded = !expanded }
            ) {
                Text("Tema", fontSize = 20.sp, color = MaterialTheme.colorScheme.tertiary)
                Text(
                    getPreference(),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onTertiary
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = MaterialTheme.colorScheme.background
            ) {
                DropdownMenuItem(
                    text = { Text("Automático") },
                    onClick = {
                        expanded = false
                        setPreference("Automático")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Claro") },
                    onClick = {
                        expanded = false
                        setPreference("Claro")
                    }
                )
                DropdownMenuItem(
                    text = { Text("Escuro") },
                    onClick = {
                        expanded = false
                        setPreference("Escuro")
                    }
                )
            }
        }
    }
}