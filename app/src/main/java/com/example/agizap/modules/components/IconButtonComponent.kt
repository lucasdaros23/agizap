package com.example.agizap.modules.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonComponent(painter: Painter, onClick: () -> Unit, size: Int) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(size.dp)
        )
    }
}