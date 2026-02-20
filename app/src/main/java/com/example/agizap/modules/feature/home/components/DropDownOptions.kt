package com.example.agizap.modules.feature.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.agizap.R
import com.example.agizap.modules.components.IconButtonComponent

@Composable
fun DropDownOptions(onClickLogout: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButtonComponent(
            painter = painterResource(R.drawable.opcoes),
            onClick = { expanded = !expanded },
            size = 20
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Sair") },
                onClick = {
                    expanded = false
                    onClickLogout()
                }
            )
        }
    }
}