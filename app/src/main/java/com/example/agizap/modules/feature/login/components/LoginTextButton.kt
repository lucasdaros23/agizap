package com.example.agizap.modules.feature.login.components

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun LoginTextButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        content = { Text(text) },
    )
}