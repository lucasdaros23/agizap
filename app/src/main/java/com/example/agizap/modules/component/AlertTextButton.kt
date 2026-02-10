package com.example.agizap.modules.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun AlertTextButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text(text, color = MaterialTheme.colorScheme.primary)
    }
}