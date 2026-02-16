package com.example.agizap.modules.feature.register.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun ReturnButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() },
    )
    {
        Text(
            "<",
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.tertiary
        )
    }

}